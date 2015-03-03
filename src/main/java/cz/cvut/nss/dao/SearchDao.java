package cz.cvut.nss.dao;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;

import java.util.Date;
import java.util.List;

/**
 * Search dao - to perform path finding.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public interface SearchDao {

    /**
     * will find paths.
     * @param stationFromId station from
     * @param stationToId station to
     * @param departure time departure
     * @param maxDays how many days in the future
     * @return wrapper with search results - founded paths
     */
    List<SearchResultWrapper> findRides(long stationFromId, long stationToId, Date departure, int maxDays);

    List<SearchResultWrapper> findRidesNew(long stationFromId, long stationToId, Date departure, int maxDays);

}
