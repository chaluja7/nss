package cz.cvut.nss.services.neo4j.impl;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.utils.comparator.SearchResultByDepartureDateComparator;
import cz.cvut.nss.utils.filter.SearchResultFilter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Neo4j implementace search service.
 *
 * @author jakubchalupa
 * @since 31.03.15
 */
@Service
@Transactional("neo4jTransactionManager")
public class Neo4jSearchService implements SearchService {

    @Autowired
    @Qualifier("neo4jSearchDao")
    protected SearchDao searchDao;

    @Override
    public List<SearchResultWrapper> findPathByDepartureDate(long stationFromId, long stationToId, Date departure, int maxHoursAfterDeparture, int maxTransfers, int maxResults) {

        DateTime departureDateTime = new DateTime(departure);
        DateTime maxDateDeparture = departureDateTime.plusHours(maxHoursAfterDeparture);

        //vytahnu vsechny moznosti, jak docestovat z from do to
        //zde jsou ovsem cesty, ktere nechci uzivatele zobrazit. Napr. muzu jet po stopech 1 - 3 - 5, nebo 1 - 3 - 4 - 5. V tomto pripade chci pouze tu cestu, ktera je tam drive a (nebo) rychleji.
        //nema cenu uzivateli zobrazovat uplne vsechny moznosti
        List<SearchResultWrapper> resultList = searchDao.findRidesByDepartureDate(stationFromId, stationToId, departure, maxDateDeparture.toDate(), maxTransfers);

        //seradim vysledky vlastnim algoritmem
        Collections.sort(resultList, new SearchResultByDepartureDateComparator());

        //vratim jen ty nejrelevantnejsi vyfiltrovane vysledky
        List<SearchResultWrapper> filteredList = SearchResultFilter.getFilteredResults(resultList);

        if(filteredList.size() <= maxResults || maxResults < 0) {
            return filteredList;
        }

        List<SearchResultWrapper> retList = new ArrayList<>(maxResults);
        for(int i = 0; i < maxResults; i++) {
            retList.add(i, filteredList.get(i));
        }

        return retList;
    }

    @Override
    public List<SearchResultWrapper> findPathByArrivalDate(long stationFromId, long stationToId, Date arrival, int maxHoursBeforeArrival, int maxTransfers, int maxResults) {

        DateTime arrivalDateTime = new DateTime(arrival);
        DateTime minDateArrival = arrivalDateTime.minusHours(maxHoursBeforeArrival);

        //vytahnu vsechny moznosti, jak docestovat z from do to
        //zde jsou ovsem cesty, ktere nechci uzivatele zobrazit. Napr. muzu jet po stopech 1 - 3 - 5, nebo 1 - 3 - 4 - 5. V tomto pripade chci pouze tu cestu, ktera je tam drive a (nebo) rychleji.
        //nema cenu uzivateli zobrazovat uplne vsechny moznosti
        List<SearchResultWrapper> resultList = searchDao.findRidesByArrivalDate(stationFromId, stationToId, arrival, minDateArrival.toDate(), maxTransfers);

        //seradim vysledky vlastnim algoritmem
        Collections.sort(resultList, new SearchResultByDepartureDateComparator());

        //vratim jen ty nejrelevantnejsi vyfiltrovane vysledky
        List<SearchResultWrapper> filteredList = SearchResultFilter.getFilteredResults(resultList);

        if(filteredList.size() <= maxResults || maxResults < 0) {
            return filteredList;
        }

        List<SearchResultWrapper> retList = new ArrayList<>(maxResults);
        for(int i = 0; i < maxResults; i++) {
            retList.add(filteredList.get(filteredList.size() - (maxResults - i)));
        }

        return retList;
    }


}
