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
     * @param maxDays max pocet dni od min odjezdu
     * @param maxTransfers max pocet prestupu
     * @return list vysledku
     */
    List<SearchResultWrapper> findPathByDepartureDate(long stationFromId, long stationToId, Date departure, int maxDays, int maxTransfers);
}
