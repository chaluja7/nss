package cz.cvut.nss.dao;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;

import java.util.Date;
import java.util.List;

/**
 * Search dao - k vyhledavani spoju.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public interface SearchDao {

    /**
     * najde cesty (paths) ze stanice do stanice dle data odjezdu
     * @param stationFromId stanice z
     * @param stationToId stanice do
     * @param departure odjezd
     * @param maxDeparture max odjezd
     * @param maxTransfers max pocet prestupu
     * @return list vysledku
     */
    List<SearchResultWrapper> findRidesByDepartureDate(long stationFromId, long stationToId, Date departure, Date maxDeparture, int maxTransfers);

    /**
     * najde cesty (paths) ze stanice do stanice dle data prijezdu
     * @param stationFromId stanice z
     * @param stationToId stanice do
     * @param arrival prijezd
     * @param minArrival min prijezd
     * @param maxTransfers max pocet prestupu
     * @return list vysledku
     */
    List<SearchResultWrapper> findRidesByArrivalDate(long stationFromId, long stationToId, Date arrival, Date minArrival, int maxTransfers);

}
