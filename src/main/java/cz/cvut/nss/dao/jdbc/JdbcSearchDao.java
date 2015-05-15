package cz.cvut.nss.dao.jdbc;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.SearchWrappers.StopSearchWrapper;
import cz.cvut.nss.SearchWrappers.VisitedStopsWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.dao.StopDao;
import cz.cvut.nss.utils.CollectionCloneUtils;
import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Time;
import java.util.*;

/**
 * Dao pro hledani spoju, implementace JDBC.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public class JdbcSearchDao extends JdbcDaoSupport implements SearchDao {

    @Autowired
    protected StopDao stopDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<SearchResultWrapper> findRidesByDepartureDate(long stationFromId, long stationToId, Date departure, Date maxDeparture, int maxTransfers) {
        //ridesMap se mi naplni nalezenymi spoji ve vyhledavacim algoritmu (funkce findRidesByDepartureDateAlgorithm)
        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();
        new RecursiveFinder().findRidesByDepartureDateAlgorithm(stationFromId, stationToId, departure, maxDeparture, 0, ridesMap,
                new ArrayList<VisitedStopsWrapper>(), new ArrayList<Long>(), maxTransfers, new HashMap<Long, List<Long>>());

        //vysledky vyhledavani dam do listu a vratim. momentalne tam jsou vysledky, ktere dale musi byt vyfiltrovany!
        return transformSearchResultWrapperMapToList(ridesMap);
    }

    @Override
    public List<SearchResultWrapper> findRidesByArrivalDate(long stationFromId, long stationToId, Date arrival, Date minArrival, int maxTransfers) {
        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();
        new RecursiveFinder().findRidesByArrivalDateAlgorithm(stationFromId, stationToId, arrival, minArrival, 0, ridesMap,
                new ArrayList<VisitedStopsWrapper>(), new ArrayList<Long>(), maxTransfers, new HashMap<Long, List<Long>>());

        return transformSearchResultWrapperMapToList(ridesMap);
    }

    /**
     * Trida pro hledani rekurzivnim algoritmem. Pri hledani se vzdy vytvori nova instance (v atributech se ukladaji pomocne mezivysledky)
     */
    private class RecursiveFinder {

        private final Map<Long, Long> visitedStopsGlobal = new HashMap<>();

        /**
         * Naplni list ridesMap vysledky hledani cesty.
         * @param stationFromId stanice z
         * @param stationToId stanice do
         * @param departure datum odjezdu
         * @param maxDateDeparture max datum odjezdu
         * @param stepNumber v kolikatem cyklu rekurze jsem
         * @param ridesMap mapa RidesID => searchResultWrapper (stopy) s vysledky hledani. Pro vsechny mozne RidesID bude v mape samozrejme ten nejlepsi vysledek
         * @param visitedStops jiz navstivene stopy v ramci rekurze (abych do nich nelezl znovu) a abych znal nalezenou cestu
         * @param visitedRides jiz navstivene ridy v ramci rekurze (abych do nich nelezl znovu) a abych znal nalezenou cestu
         * @param maxTransfers max pocet prestupu (max zanoreni rekurze)
         */
        protected void findRidesByDepartureDateAlgorithm(long stationFromId, long stationToId, Date departure, Date maxDateDeparture, int stepNumber,
                                                         Map<String, SearchResultWrapper> ridesMap, List<VisitedStopsWrapper> visitedStops, List<Long> visitedRides,
                                                         int maxTransfers, Map<Long, List<Long>> visitedStations) {

            LocalDateTime tempDateDeparture = new LocalDateTime(departure);
            LocalDateTime tempMaxDateDeparture = new LocalDateTime(maxDateDeparture);

            if(tempMaxDateDeparture.isBefore(tempDateDeparture)) {
                return;
            }

            //Nejdrive najdu vsechny stopy, ktere jsou na stanici, ze ktere jedu serazene dle data odjezdu
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("stationId", stationFromId);
            namedParameters.addValue("trueParam", true);
            namedParameters.addValue("departureDate", new java.sql.Date(departure.getTime()));
            namedParameters.addValue("departure", new Time(departure.getTime()));
            namedParameters.addValue("maxDeparture", new Time(maxDateDeparture.getTime()));
            if(visitedRides.size() == 0) {
                List<Long> tempList = new ArrayList<>();
                tempList.add(0l);
                namedParameters.addValue("visitedRides", tempList);
            } else {
                namedParameters.addValue("visitedRides", visitedRides);
            }

            StringBuilder initialStopsSql = new StringBuilder().append("select s.id, s.ride_id, s.station_id, s.departure, s.arrival, s.stopOrder from stops s " +
                    "inner join rides r on s.ride_id = r.id inner join operation_intervals i on r.operation_interval_id = i.id " +
                    "where s.station_id = :stationId and s.ride_id not in(:visitedRides) ");

            //osetreni, jestli nejedu zpet na stanici, ze ktere jsem prijel
            if(visitedStops.size() > 0) {
                initialStopsSql.append("and not exists (select id from stops where station_id = (select station_id from stops where id = :prevStopId) ")
                        .append("and ride_id = s.ride_id and stopOrder = s.stopOrder + 1) ");

                namedParameters.addValue("prevStopId", visitedStops.get(visitedStops.size() - 1).getStopId());
            }

            //osetreni prehoupnuti pres pulnoc
            if(tempMaxDateDeparture.getMillisOfDay() > tempDateDeparture.getMillisOfDay()) {
                //neprehoupl jsem se pres pulnoc
                initialStopsSql.append("and i.startDate <= :departureDate and i.endDate >= :departureDate ");
                initialStopsSql.append("and ").append(getDayOfWeekCondition(tempDateDeparture.getDayOfWeek())).append(" = :trueParam ");
                initialStopsSql.append("and s.departure >= :departure and s.departure <= :maxDeparture ");
                initialStopsSql.append("order by s.departure");
            } else {
                //prehoupl jsem se pres pulnoc
                initialStopsSql.append("and ((i.startDate <= :departureDate and i.endDate >= :departureDate ");
                initialStopsSql.append("and ").append(getDayOfWeekCondition(tempDateDeparture.getDayOfWeek())).append(" = :trueParam ");
                initialStopsSql.append("and s.departure >= :departure) ");

                initialStopsSql.append("or (i.startDate <= :maxDepartureDate and i.endDate >= :maxDepartureDate ");
                initialStopsSql.append("and ").append(getDayOfWeekCondition(tempMaxDateDeparture.getDayOfWeek())).append(" = :trueParam ");
                initialStopsSql.append("and s.departure < :maxDeparture)) ");

                initialStopsSql.append("order by case when s.departure < :departure then 2 else 1 end, s.departure");
                namedParameters.addValue("maxDepartureDate", new java.sql.Date(maxDateDeparture.getTime()));
            }

            List<StopSearchWrapper> stops = namedParameterJdbcTemplate.query(initialStopsSql.toString(), namedParameters, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

            //pro tyto stopy iteruji
            for(StopSearchWrapper stop : stops) {

                //nyni vyberu vsechny stopy nachazejici se na Ride, ktera pokracuje ze stopu v iteraci
                List<Long> newVisitedRides = new ArrayList<>(visitedRides);
                newVisitedRides.add(stop.getRideId());

                Object[] params;
                String rideSql = "select id, ride_id, station_id, departure, arrival, stopOrder from stops where ride_id = ? and stopOrder > ? ";
                //osetreni prehoupnuti pres pulnoc
                if(tempMaxDateDeparture.getMillisOfDay() > tempDateDeparture.getMillisOfDay()) {
                    //neprehoupl jsem se pres pulnoc
                    rideSql += "and arrival < ? ";
                    params = new Object[]{stop.getRideId(), stop.getStopOrder(), new Time(maxDateDeparture.getTime())};
                } else {
                    //prehoupl jsem se pres pulnoc
                    rideSql += "and (arrival < ? or arrival >= ?) ";
                    params = new Object[]{stop.getRideId(), stop.getStopOrder(), new Time(maxDateDeparture.getTime()), new Time(departure.getTime())};
                }

                rideSql += "order by stopOrder asc";

                List<StopSearchWrapper> stopsOnRide =
                        getJdbcTemplate().
                                query(rideSql, params, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

                //pro vsechny nalezene stopy iteruji
                for(StopSearchWrapper stopOnRide : stopsOnRide) {

                    //vratil jsem se na stanici, ze ktere jsem vyjizdel
                    if(stopOnRide.getStationId() == stationFromId) {
                        break;
                    }

                    Map<Long, List<Long>> visitedStationsNew = CollectionCloneUtils.cloneMap(visitedStations);
                    //v ramci teto path jsem na teto stanici jiz byl (tedy se vracim, coz je nezadouci)
                    //ovsem v ramci jedne ridy muzu na jednu stanici vicenasobne
                    boolean breakMe = false;
                    if(visitedStationsNew.containsKey(stopOnRide.getStationId())) {
                        List<Long> ridesWithThisStation = visitedStationsNew.get(stopOnRide.getStationId());
                        for(long r : ridesWithThisStation) {
                            if(r != stopOnRide.getRideId()) {
                                breakMe = true;
                                break;
                            }
                        }
                    } else {
                        visitedStationsNew.put(stopOnRide.getStationId(), new ArrayList<Long>());
                    }
                    if(breakMe) {
                        break;
                    }
                    visitedStationsNew.get(stopOnRide.getStationId()).add(stopOnRide.getRideId());


                    LocalTime arrivalDayTime = new LocalTime(stopOnRide.getArrival());
                    int millisOfArrivalDay = arrivalDayTime.getMillisOfDay();

                    LocalDateTime currentStopArrivalDateTime = new LocalDateTime(departure);
                    if(millisOfArrivalDay < currentStopArrivalDateTime.getMillisOfDay()) {
                        //prehoupl jsem se pres pulnoc
                        currentStopArrivalDateTime = currentStopArrivalDateTime.plusDays(1);
                    }

                    currentStopArrivalDateTime = currentStopArrivalDateTime.millisOfDay().withMinimumValue();
                    currentStopArrivalDateTime = currentStopArrivalDateTime.plusMillis(millisOfArrivalDay);

                    //pridani navstivenych stopu
                    List<VisitedStopsWrapper> newVisitedStops = new ArrayList<>(visitedStops.size());
                    for(VisitedStopsWrapper w : visitedStops) {
                        newVisitedStops.add(w.getDeepCopy());
                    }

                    VisitedStopsWrapper w1 = new VisitedStopsWrapper(stop.getId(), new LocalTime(stop.getDeparture()).getMillisOfDay());
                    VisitedStopsWrapper w2 = new VisitedStopsWrapper(stopOnRide.getId(), currentStopArrivalDateTime.getMillisOfDay());
                    newVisitedStops.add(w1);
                    newVisitedStops.add(w2);

                    //kontrola casu v aktualnim nodu, pokud jsem jiz byl v lepsim case tak nepokracuji :)
                    VisitedStopsWrapper departureStop = newVisitedStops.get(0);
                    long travelTime;
                    int departureMillis = (int) departureStop.getDepartureMillis();
                    if(millisOfArrivalDay > departureMillis) {
                        travelTime = millisOfArrivalDay - departureMillis;
                    } else {
                        travelTime = (DateTimeUtils.MILLIS_IN_DAY - departureMillis) + millisOfArrivalDay;
                    }

                    //pareto-optimalita
                    if(visitedStopsGlobal.containsKey(stopOnRide.getId())) {
                        //a byl jsem na nem v pro me s priznivejsim casem vyjezdu
                        if(visitedStopsGlobal.get(stopOnRide.getId()) < travelTime) {
                            break;
                        }
                    }
                    visitedStopsGlobal.put(stopOnRide.getId(), travelTime);

                    if (stopOnRide.getStationId() == stationToId) {
                        //pokud jsem narazil na cilovou stanici tak ulozim cestu do ridesMapy pokud tam jiz neni na stejnych ridach s lepsim casem
                        StringBuilder stringBuilder = new StringBuilder();
                        for(Long l : newVisitedRides) {
                            if(stringBuilder.length() != 0) {
                                stringBuilder.append("-");
                            }
                            stringBuilder.append(l);
                        }

                        String pathIdentifier = stringBuilder.toString();
                        if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
                            SearchResultWrapper wrapper = new SearchResultWrapper();
                            wrapper.setDeparture(newVisitedStops.get(0).getDepartureMillis());
                            wrapper.setArrival(currentStopArrivalDateTime.toDateTime().getMillis());
                            wrapper.setTravelTime(travelTime);
                            wrapper.setNumberOfTransfers(stepNumber);
                            List<Long> visitedStopIds = new ArrayList<>();
                            for(VisitedStopsWrapper visitedStopsWrapper : newVisitedStops) {
                                visitedStopIds.add(visitedStopsWrapper.getStopId());
                            }
                            wrapper.setStops(visitedStopIds);

                            boolean overMidnightArrival = false;
                            if(currentStopArrivalDateTime.toDateTime().getMillis() < tempDateDeparture.getMillisOfDay()) {
                                overMidnightArrival = true;
                            }

                            wrapper.setOverMidnightArrival(overMidnightArrival);
                            ridesMap.put(pathIdentifier, wrapper);
                        }

                        break;
                    } else if(stepNumber < maxTransfers) {
                        //pokud toto neni cilova stanice a jeste jsem neprestoupil MAX krat, zavolam rekurzivne algoritmus
                        //vychozi stanice bude aktualni stanice z cyklu a hledam vyjezd od data, kdy je prijezd do tohoto stopu
                        findRidesByDepartureDateAlgorithm(stopOnRide.getStationId(), stationToId, currentStopArrivalDateTime.toDate(), maxDateDeparture,
                                stepNumber + 1, ridesMap, newVisitedStops, newVisitedRides, maxTransfers, visitedStationsNew);
                    }
                }
            }
        }

        protected void findRidesByArrivalDateAlgorithm(long stationFromId, long stationToId, Date arrival, Date minDateArrival, int stepNumber,
                                                       Map<String, SearchResultWrapper> ridesMap, List<VisitedStopsWrapper> visitedStops, List<Long> visitedRides,
                                                       int maxTransfers, Map<Long, List<Long>> visitedStations) {

            LocalDateTime tempDateArrival = new LocalDateTime(arrival);
            LocalDateTime tempMinDateArrival = new LocalDateTime(minDateArrival);

            if(tempDateArrival.isBefore(tempMinDateArrival)) {
                return;
            }

            //Nejdrive najdu vsechny stopy, ktere jsou na stanici, do ktere jedu serazene dle data odjezdu
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("stationId", stationToId);
            namedParameters.addValue("trueParam", true);
            namedParameters.addValue("arrivalDate", new java.sql.Date(arrival.getTime()));
            namedParameters.addValue("arrival", new Time(arrival.getTime()));
            namedParameters.addValue("minArrival", new Time(minDateArrival.getTime()));
            if(visitedRides.size() == 0) {
                List<Long> tempList = new ArrayList<>();
                tempList.add(0l);
                namedParameters.addValue("visitedRides", tempList);
            } else {
                namedParameters.addValue("visitedRides", visitedRides);
            }

            StringBuilder initialStopsSql = new StringBuilder().append("select s.id, s.ride_id, s.station_id, s.departure, s.arrival, s.stopOrder from stops s " +
                    "inner join rides r on s.ride_id = r.id inner join operation_intervals i on r.operation_interval_id = i.id " +
                    "where s.station_id = :stationId and s.ride_id not in(:visitedRides) ");

            //osetreni, jestli nejedu zpet na stanici, ze ktere jsem prijel
            if(visitedStops.size() > 0) {
                initialStopsSql.append("and not exists (select id from stops where station_id = (select station_id from stops where id = :prevStopId) ")
                        .append("and ride_id = s.ride_id and stopOrder = s.stopOrder - 1) ");

                namedParameters.addValue("prevStopId", visitedStops.get(0).getStopId());
            }

            //osetreni prehoupnuti pres pulnoc
            if(tempMinDateArrival.getMillisOfDay() < tempDateArrival.getMillisOfDay()) {
                //neprehoupl jsem se pres pulnoc
                initialStopsSql.append("and i.startDate <= :arrivalDate and i.endDate >= :arrivalDate ");
                initialStopsSql.append("and ").append(getDayOfWeekCondition(tempDateArrival.getDayOfWeek())).append(" = :trueParam ");
                initialStopsSql.append("and s.arrival <= :arrival and s.arrival >= :minArrival ");
                initialStopsSql.append("order by s.arrival desc");
            } else {
                //prehoupl jsem se pres pulnoc
                initialStopsSql.append("and ((i.startDate <= :arrivalDate and i.endDate >= :arrivalDate ");
                initialStopsSql.append("and ").append(getDayOfWeekCondition(tempDateArrival.getDayOfWeek())).append(" = :trueParam ");
                initialStopsSql.append("and s.arrival <= :arrival) ");

                initialStopsSql.append("or (i.startDate <= :minDateArrival and i.endDate >= :minDateArrival ");
                initialStopsSql.append("and ").append(getDayOfWeekCondition(tempMinDateArrival.getDayOfWeek())).append(" = :trueParam ");
                initialStopsSql.append("and s.arrival > :minArrival)) ");
                initialStopsSql.append("order by case when s.arrival < :arrival then 1 else 2 end, s.arrival desc");

                namedParameters.addValue("minDateArrival", new java.sql.Date(minDateArrival.getTime()));
            }

            List<StopSearchWrapper> stops = namedParameterJdbcTemplate.query(initialStopsSql.toString(), namedParameters, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

            //pro tyto stopy iteruji
            for(StopSearchWrapper stop : stops) {

                //nyni vyberu vsechny stopy nachazejici se na Ride, ktera predchazi stopu v iteraci
                List<Long> newVisitedRides = new ArrayList<>(visitedRides);
                newVisitedRides.add(0, stop.getRideId());

                Object[] params;
                String rideSql = "select id, ride_id, station_id, departure, arrival, stopOrder from stops where ride_id = ? and stopOrder < ? ";
                //osetreni prehoupnuti pres pulnoc
                if(tempMinDateArrival.getMillisOfDay() < tempDateArrival.getMillisOfDay()) {
                    //neprehoupl jsem se pres pulnoc
                    rideSql += "and departure > ? ";
                    params = new Object[]{stop.getRideId(), stop.getStopOrder(), new Time(minDateArrival.getTime())};
                } else {
                    rideSql += "and (departure > ? or departure <= ?) ";
                    params = new Object[]{stop.getRideId(), stop.getStopOrder(), new Time(minDateArrival.getTime()), new Time(arrival.getTime())};
                }

                rideSql += "order by stopOrder desc";

                List<StopSearchWrapper> stopsOnRide =
                        getJdbcTemplate().
                                query(rideSql, params, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

                //pro vsechny nalezen stopy iteruji
                for(StopSearchWrapper stopOnRide : stopsOnRide) {

                    //vratil jsem se na stanici, ze ktere jsem vyjizdel
                    if(stopOnRide.getStationId() == stationToId) {
                        break;
                    }

                    Map<Long, List<Long>> visitedStationsNew = CollectionCloneUtils.cloneMap(visitedStations);
                    //v ramci teto path jsem na teto stanici jiz byl (tedy se vracim, coz je nezadouci)
                    //ovsem v ramci jedne ridy muzu na jednu stanici vicenasobne
                    boolean breakMe = false;
                    if(visitedStationsNew.containsKey(stopOnRide.getStationId())) {
                        List<Long> ridesWithThisStation = visitedStationsNew.get(stopOnRide.getStationId());
                        for(long r : ridesWithThisStation) {
                            if(r != stopOnRide.getRideId()) {
                                breakMe = true;
                                break;
                            }
                        }
                    } else {
                        visitedStationsNew.put(stopOnRide.getStationId(), new ArrayList<Long>());
                    }
                    if(breakMe) {
                        break;
                    }
                    visitedStationsNew.get(stopOnRide.getStationId()).add(stopOnRide.getRideId());


                    LocalTime departureDateTime = new LocalTime(stopOnRide.getDeparture());
                    int millisOfDepartureDay = departureDateTime.getMillisOfDay();

                    LocalDateTime currentStopDepartureDateTime = new LocalDateTime(arrival);
                    if(millisOfDepartureDay > currentStopDepartureDateTime.getMillisOfDay()) {
                        //prehoupl jsem se pres pulnoc
                        currentStopDepartureDateTime = currentStopDepartureDateTime.minusDays(1);
                    }

                    currentStopDepartureDateTime = currentStopDepartureDateTime.millisOfDay().withMinimumValue();
                    currentStopDepartureDateTime = currentStopDepartureDateTime.plusMillis(millisOfDepartureDay);

                    //pridani navstivenych stopu
                    List<VisitedStopsWrapper> newVisitedStops = new ArrayList<>(visitedStops.size());
                    for(VisitedStopsWrapper w : visitedStops) {
                        newVisitedStops.add(w.getDeepCopy());
                    }

                    VisitedStopsWrapper w1 = new VisitedStopsWrapper(stop.getId(), new LocalTime(stop.getArrival()).getMillisOfDay());
                    VisitedStopsWrapper w2 = new VisitedStopsWrapper(stopOnRide.getId(), currentStopDepartureDateTime.getMillisOfDay());
                    newVisitedStops.add(0, w1);
                    newVisitedStops.add(0, w2);

                    //kontrola casu v aktualnim nodu, pokud jsem jiz byl v lepsim case tak nepokracuji :)
                    VisitedStopsWrapper arrivalStop = newVisitedStops.get(newVisitedStops.size() - 1);
                    long travelTime;
                    int arrivalMillis = (int) arrivalStop.getDepartureMillis();
                    if(millisOfDepartureDay < arrivalMillis) {
                        //neprehoupl jsem se pres pulnoc
                        travelTime = arrivalMillis - millisOfDepartureDay;
                    } else {
                        travelTime = (DateTimeUtils.MILLIS_IN_DAY - millisOfDepartureDay) + arrivalMillis;
                    }

                    //pareto-optimalita
                    if(visitedStopsGlobal.containsKey(stopOnRide.getId())) {
                        //a byl jsem na nem v pro me s priznivejsim casem vyjezdu
                        if(visitedStopsGlobal.get(stopOnRide.getId()) < travelTime) {
                            break;
                        }
                    }
                    visitedStopsGlobal.put(stopOnRide.getId(), travelTime);

                    if (stopOnRide.getStationId() == stationFromId) {
                        //pokud jsem narazil na cilovou stanici tak ulozim cestu do ridesMapy pokud tam jiz neni na stejnych ridach s lepsim casem
                        StringBuilder stringBuilder = new StringBuilder();
                        for(Long l : newVisitedRides) {
                            if(stringBuilder.length() != 0) {
                                stringBuilder.append("-");
                            }
                            stringBuilder.append(l);
                        }

                        String pathIdentifier = stringBuilder.toString();

                        if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
                            SearchResultWrapper wrapper = new SearchResultWrapper();
                            wrapper.setArrival(newVisitedStops.get(newVisitedStops.size() - 1).getDepartureMillis());
                            wrapper.setDeparture(newVisitedStops.get(0).getDepartureMillis());
                            wrapper.setTravelTime(travelTime);
                            wrapper.setNumberOfTransfers(stepNumber);
                            List<Long> visitedStopIds = new ArrayList<>();
                            for(VisitedStopsWrapper visitedStopsWrapper : newVisitedStops) {
                                visitedStopIds.add(visitedStopsWrapper.getStopId());
                            }
                            wrapper.setStops(visitedStopIds);

                            boolean overMidnightArrival = false;
                            if(newVisitedStops.get(newVisitedStops.size() - 1).getDepartureMillis() < tempMinDateArrival.getMillisOfDay()) {
                                overMidnightArrival = true;
                            }

                            wrapper.setOverMidnightArrival(overMidnightArrival);
                            ridesMap.put(pathIdentifier, wrapper);
                        }

                        break;
                    } else if(stepNumber < maxTransfers) {
                        //pokud toto neni cilova stanice a jeste jsem neprestoupil MAX krat, zavolam rekurzivne algoritmus
                        //vychozi stanice bude aktualni stanice z cyklu a hledam vyjezd od data, kdy je prijezd do tohoto stopu
                        findRidesByArrivalDateAlgorithm(stationFromId, stopOnRide.getStationId(), currentStopDepartureDateTime.toDate(), minDateArrival,
                                stepNumber + 1, ridesMap, newVisitedStops, newVisitedRides, maxTransfers, visitedStationsNew);
                    }
                }
            }
        }
    }

    /**
     * vrati list value hodnot z mapy
     * @param ridesMap mapa search result wrapperu
     * @return list search result wrapperu
     */
    protected List<SearchResultWrapper> transformSearchResultWrapperMapToList(Map<String, SearchResultWrapper> ridesMap) {
        List<SearchResultWrapper> resultList = new ArrayList<>();
        for(Map.Entry<String, SearchResultWrapper> entry : ridesMap.entrySet()) {
            resultList.add(entry.getValue());
        }

        return resultList;
    }

    public static String getDayOfWeekCondition(int dayOfWeek) {
        switch(dayOfWeek) {
            case 1:
                return "i.monday";
            case 2:
                return "i.tuesday";
            case 3:
                return "i.wednesday";
            case 4:
                return "i.thursday";
            case 5:
                return "i.friday";
            case 6:
                return "i.saturday";
            case 7:
                return "i.sunday";
            default:
                throw new IllegalArgumentException();
        }
    }

}
