package cz.cvut.nss.dao.jdbc;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.SearchWrappers.StopSearchWrapper;
import cz.cvut.nss.dao.SearchDao;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.*;

/**
 * Dao pro hledani spoju, implementace JDBC.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public class JdbcSearchDao extends JdbcDaoSupport implements SearchDao {

    @Override
    public List<SearchResultWrapper> findRides(long stationFromId, long stationToId, Date departure, int maxDays) {
        //RideId -> ride time in milis
        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();

        DateTime departureDateTime = new DateTime(departure);
        DateTime maxDateDeparture = departureDateTime.plusDays(maxDays);

        //Vyberu vsechny stopy ktere jsou na stationFromId serazene vzestupne dle data odjezdu
        String initialStopsSql = "select id, ride_id, station_id, departure, arrival from stops where station_id = ? and departure > ? and departure < ?" +
                "order by departure asc";

        List<StopSearchWrapper> stops =
                getJdbcTemplate().
                        query(initialStopsSql, new Object[]{stationFromId, departure, maxDateDeparture.toDate()},
                                new BeanPropertyRowMapper<>(StopSearchWrapper.class));

        //pro nalezene stopy hledam dalsi zastavky na prislusne ride
        for(StopSearchWrapper stop : stops) {
            String rideSql = "select id, ride_id, station_id, departure, arrival from stops where ride_id = ? and (departure is null or departure > ?) order by departure asc";

            List<StopSearchWrapper> stopsOnRide =
                    getJdbcTemplate().
                            query(rideSql, new Object[]{stop.getRideId(), stop.getDeparture()}, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

            for(StopSearchWrapper stopOnRide : stopsOnRide) {
                if(stopOnRide.getStationId() == stationToId) {
                    SearchResultWrapper wrapper = new SearchResultWrapper();
                    wrapper.setArrival(stopOnRide.getArrival().getTime());
                    wrapper.setTravelTime(stopOnRide.getArrival().getTime() - stop.getDeparture().getTime());

                    List<Long> rideList = new ArrayList<>();
                    rideList.add(stop.getId());
                    rideList.add(stopOnRide.getId());
                    wrapper.setStops(rideList);
                    ridesMap.put(stop.getRideId() + "", wrapper);
                    break;
                } else {


                    String initialStopsSql2 = "select id, ride_id, station_id, departure, arrival from stops where station_id = ? and departure > ? and departure < ?" +
                            "and ride_id != ? and id != ? order by departure asc";

                    List<StopSearchWrapper> stops2 =
                            getJdbcTemplate().
                                    query(initialStopsSql2, new Object[]{stopOnRide.getStationId(), stopOnRide.getArrival(), maxDateDeparture.toDate(),
                                                    stopOnRide.getRideId(), stopOnRide.getId()},
                                            new BeanPropertyRowMapper<>(StopSearchWrapper.class));

                    for(StopSearchWrapper stop2 : stops2) {
                        List<StopSearchWrapper> stopsOnRide2 =
                                getJdbcTemplate().
                                        query(rideSql, new Object[]{stop2.getRideId(), stop2.getDeparture()}, new BeanPropertyRowMapper<>(StopSearchWrapper.class));

                        boolean founded = false;
                        for(StopSearchWrapper stopOnRide2 : stopsOnRide2) {
                            if(stopOnRide2.getStationId() == stationToId) {
                                String pathIdentifier = stop.getRideId() + "-" + stop2.getRideId();
                                long travelTime = stopOnRide2.getArrival().getTime() - stop.getDeparture().getTime();

                                SearchResultWrapper wrapper = new SearchResultWrapper();
                                wrapper.setTravelTime(travelTime);
                                wrapper.setArrival(stopOnRide2.getArrival().getTime());

                                List<Long> rideList = new ArrayList<>();
                                rideList.add(stop.getId());
                                rideList.add(stopOnRide.getId());
                                rideList.add(stop2.getId());
                                rideList.add(stopOnRide2.getId());
                                wrapper.setStops(rideList);

                                if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
                                    ridesMap.put(pathIdentifier, wrapper);
                                }

                                founded = true;
                                break;
                            }
                        }

                        if(founded) {
                            break;
                        }

                    }
                }
            }

        }

        List<SearchResultWrapper> resultList = new ArrayList<>();
        for(Map.Entry<String, SearchResultWrapper> entry : ridesMap.entrySet()) {
            resultList.add(entry.getValue());
        }

        Collections.sort(resultList, new SearchResultComparator());

        List<SearchResultWrapper> finalList = new ArrayList<>();

        Set<Long> alreadyUsed = new HashSet<>();
        for(SearchResultWrapper wrapper : resultList) {
            boolean skip = false;
            for(Long stopId : wrapper.getStops()) {
                if(alreadyUsed.contains(stopId)) {
                    skip = true;
                    break;
                }
            }

            if(skip) {
                continue;
            }

            finalList.add(wrapper);
            for(Long stopId : wrapper.getStops()) {
                alreadyUsed.add(stopId);
            }

        }

        return finalList;
    }

    /**
     * slouzi pro serazeni vysledku vyhledavani spojeni
     */
    public class SearchResultComparator implements Comparator<SearchResultWrapper> {
        @Override
        public int compare(SearchResultWrapper o1, SearchResultWrapper o2) {
            if(o1.getArrival() < o2.getArrival()) {
                return -1;
            }

            if(o1.getArrival() > o2.getArrival()) {
                return 1;
            }

            return o1.getTravelTime() < o2.getTravelTime() ? -1 : 1;

        }
    }

}
