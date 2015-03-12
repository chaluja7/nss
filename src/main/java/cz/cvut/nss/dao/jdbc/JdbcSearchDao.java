package cz.cvut.nss.dao.jdbc;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.SearchWrappers.StopSearchWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.dao.StopDao;
import cz.cvut.nss.entities.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

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
        findRidesByDepartureDateAlgorithm(stationFromId, stationToId, departure, maxDeparture, 0, ridesMap, new ArrayList<Long>(), new ArrayList<Long>(), maxTransfers);

        //vysledky vyhledavani dam do listu a vratim. momentalne tam jsou vysledky, ktere dale musi byt vyfiltrovany!
        List<SearchResultWrapper> resultList = new ArrayList<>();
        for(Map.Entry<String, SearchResultWrapper> entry : ridesMap.entrySet()) {
            resultList.add(entry.getValue());
        }

        return resultList;
    }

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
    private void findRidesByDepartureDateAlgorithm(long stationFromId, long stationToId, Date departure, Date maxDateDeparture, int stepNumber,
                              Map<String, SearchResultWrapper> ridesMap, List<Long> visitedStops, List<Long> visitedRides,
                              int maxTransfers) {

        //Nejdrive najdu vsechny stopy, ktere jsou na stanici, ze ktere jedu serazene dle data odjezdu
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stationId", stationFromId);
        namedParameters.addValue("departure", departure);
        namedParameters.addValue("maxDeparture", maxDateDeparture);
        if(visitedRides.size() == 0) {
            List<Long> tempList = new ArrayList<>();
            tempList.add(0l);
            namedParameters.addValue("visitedRides", tempList);
        } else {
            namedParameters.addValue("visitedRides", visitedRides);
        }

        String initialStopsSql = "select id, ride_id, station_id, departure, arrival from stops " +
                "where station_id = :stationId and departure > :departure and departure < :maxDeparture and ride_id not in(:visitedRides) " +
                "order by departure asc";

        List<StopSearchWrapper> stops = namedParameterJdbcTemplate.query(initialStopsSql, namedParameters, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

        //pro tyto stopy iteruji
        for(StopSearchWrapper stop : stops) {

            //nyni vyberu vsechny stopy nachazejici se na Ride, ktera pokracuje ze stopu v iteraci
            List<Long> newVisitedRides = new ArrayList<>(visitedRides);
            newVisitedRides.add(stop.getRideId());

            String rideSql = "select id, ride_id, station_id, departure, arrival from stops where ride_id = ? and (departure is null or departure > ?) order by departure asc ";

            List<StopSearchWrapper> stopsOnRide =
                    getJdbcTemplate().
                            query(rideSql, new Object[]{stop.getRideId(), stop.getDeparture()}, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

            //pro vsechny nalezen stopy iteruji
            for(StopSearchWrapper stopOnRide : stopsOnRide) {

                List<Long> newVisitedStops = new ArrayList<>(visitedStops);
                newVisitedStops.add(stop.getId());
                newVisitedStops.add(stopOnRide.getId());

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

                    Stop departureStop = stopDao.find(newVisitedStops.get(0));
                    long travelTime = stopOnRide.getArrival().getTime() - departureStop.getDeparture().toDate().getTime();
                    if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
                        SearchResultWrapper wrapper = new SearchResultWrapper();
                        wrapper.setArrival(stopOnRide.getArrival().getTime());
                        wrapper.setTravelTime(travelTime);
                        wrapper.setStops(newVisitedStops);
                        ridesMap.put(pathIdentifier, wrapper);
                    }

                    break;
                } else if(stepNumber < maxTransfers) {
                    //pokud toto neni cilova stanice a jeste jsem neprestoupil MAX krat, zavolam rekurzivne algoritmus
                    //vychozi stanice bude aktualni stanice z cyklu a hledam vyjezd od data, kdy je prijezd do tohoto stopu
                    findRidesByDepartureDateAlgorithm(stopOnRide.getStationId(), stationToId, stopOnRide.getArrival(), maxDateDeparture,
                            stepNumber + 1, ridesMap, newVisitedStops, newVisitedRides, maxTransfers);
                }
            }
        }
    }





}
