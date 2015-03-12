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

    List<SearchResultWrapper> findRidesByDepartureDate(long stationFromId, long stationToId, Date departure, Date maxDeparture, int maxTransfers);

    List<SearchResultWrapper> findRidesByArrivalDate(long stationFromId, long stationToId, Date arrival, Date minArrival, int maxTransfers);

}
