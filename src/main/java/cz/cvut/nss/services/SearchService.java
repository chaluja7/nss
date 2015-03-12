package cz.cvut.nss.services;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;

import java.util.Date;
import java.util.List;

/**
 * Common interface for all search engines.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public interface SearchService {

    /**
     * Najde spojeni dle data odjezdu
     * @param stationFromId stanice z
     * @param stationToId stanice do
     * @param departure datum odjezdu
     * @param maxHoursAfterDeparture max pocet hodin, o ktere muze spoj vyjet pozdeji nez departure
     * @param maxTransfers max pocet prestupu
     * @param maxResults max pocet vracenych vysledku
     * @return list vysledku
     */
    List<SearchResultWrapper> findPathByDepartureDate(long stationFromId, long stationToId, Date departure, int maxHoursAfterDeparture, int maxTransfers, int maxResults);

    /**
     * Najde spojeni dle data prijezdu
     * @param stationFromId stanice z
     * @param stationToId stanice do
     * @param arrival datum prijezdu
     * @param maxHoursBeforeArrival max pocet hodin, o ktere muze spoj prijet drive nez je arrival
     * @param maxTransfers max pocet prestupu
     * @param maxResults max pocet vracenych vysledku
     * @return list vysledku
     */
    List<SearchResultWrapper> findPathByArrivalDate(long stationFromId, long stationToId, Date arrival, int maxHoursBeforeArrival, int maxTransfers, int maxResults);

}
