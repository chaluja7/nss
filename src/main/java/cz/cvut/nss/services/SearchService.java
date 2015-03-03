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
     * will find paths
     * @param stationFromId
     * @param stationToId
     * @param departure
     * @param maxDays
     * @return
     */
    List<SearchResultWrapper> findPath(long stationFromId, long stationToId, Date departure, int maxDays);

    List<SearchResultWrapper> findPathNew(long stationFromId, long stationToId, Date departure, int maxDays);
}
