package cz.cvut.nss.services.impl;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.utils.comparator.SearchResultByDepartureDateComparator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Implementation of SearchService.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    protected SearchDao searchDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
        List<SearchResultWrapper> filteredList = this.getFilteredResults(resultList);

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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<SearchResultWrapper> findPathByArrivalDate(long stationFromId, long stationToId, Date arrival, int maxHoursBeforeArrival, int maxTransfers, int maxResults) {
        DateTime arrivalDateTime = new DateTime(arrival);
        DateTime minDateArrival = arrivalDateTime.minusHours(maxHoursBeforeArrival);

        List<SearchResultWrapper> resultList = searchDao.findRidesByArrivalDate(stationFromId, stationToId, arrival, minDateArrival.toDate(), maxTransfers);

        //seradim vysledky vlastnim algoritmem
        Collections.sort(resultList, new SearchResultByDepartureDateComparator());

        //vratim jen ty nejrelevantnejsi vyfiltrovane vysledky
        List<SearchResultWrapper> filteredList = this.getFilteredResults(resultList);

        if(filteredList.size() <= maxResults || maxResults < 0) {
            return filteredList;
        }

        List<SearchResultWrapper> retList = new ArrayList<>();
        for(int i = 0; i < maxResults; i++) {
            retList.add(filteredList.get(filteredList.size() - (maxResults - i)));
        }

        return retList;
    }

    /**
     * vyfiltruje jen relevantni search resulty
     * @param resultList vsechny vysledky vyhledavani - SERAZENE!
     * @return list vyfiltrovanych nejlepsich vysledku
     */
    private List<SearchResultWrapper> getFilteredResults(List<SearchResultWrapper> resultList) {
        List<SearchResultWrapper> finalList = new ArrayList<>();
        Set<Long> alreadyUsed = new HashSet<>();
        //kazdy STOP uzivateli zobrazim pouze na te nejlepsi trase.
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

}
