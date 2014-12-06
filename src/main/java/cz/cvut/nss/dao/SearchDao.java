package cz.cvut.nss.dao;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;

import java.util.Date;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 06.12.14
 */
public interface SearchDao {

    List<SearchResultWrapper>findRides(long stationFromId, long stationToId, Date departure, int maxDays);

}
