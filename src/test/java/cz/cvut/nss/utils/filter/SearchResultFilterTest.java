package cz.cvut.nss.utils.filter;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.utils.comparator.SearchResultByDepartureDateComparator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 30.12.16
 */
public class SearchResultFilterTest {

    @Test
    public void testGetFilteredResultsSame() {
        List<SearchResultWrapper> wrappers = getSearchResultWrappers();
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());

        List<SearchResultWrapper> filteredResults = SearchResultFilter.getFilteredResults(wrappers);
        Assert.assertNotNull(filteredResults);
        Assert.assertTrue(filteredResults.size() == 2);
    }

    @Test
    public void testGetFilteredResultsFirstBest() {
        List<SearchResultWrapper> wrappers = getSearchResultWrappers();

        SearchResultWrapper w1 = wrappers.get(0);
        SearchResultWrapper w2 = wrappers.get(1);
        w1.setArrival(10);
        w1.setDeparture(0);
        w1.setTravelTime(10);
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());

        List<SearchResultWrapper> filteredResults = SearchResultFilter.getFilteredResults(wrappers);
        Assert.assertNotNull(filteredResults);
        Assert.assertTrue(filteredResults.size() == 1);
        Assert.assertTrue(filteredResults.get(0) == w2);
    }


    @Test
    public void testGetFilteredResultsSameStops() {
        List<SearchResultWrapper> wrappers = getSearchResultWrappers();

        SearchResultWrapper w1 = wrappers.get(0);
        SearchResultWrapper w2 = wrappers.get(1);

        w2.getStops().set(0, 5l);
        w2.setArrival(10);
        w2.setDeparture(5);
        w2.setTravelTime(5);
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());

        List<SearchResultWrapper> filteredResults = SearchResultFilter.getFilteredResults(wrappers);
        Assert.assertNotNull(filteredResults);
        Assert.assertTrue(filteredResults.size() == 1);
        Assert.assertTrue(filteredResults.get(0) == w1);
    }


    private List<SearchResultWrapper> getSearchResultWrappers() {
        SearchResultWrapper w1 = new SearchResultWrapper();
        w1.setDeparture(1);
        w1.setArrival(2);
        w1.setOverMidnightArrival(false);
        w1.setTravelTime(1);
        w1.setNumberOfTransfers(0);

        List<Long> stops1 = new ArrayList<>();
        stops1.add(5l);
        stops1.add(6l);
        w1.setStops(stops1);

        SearchResultWrapper w2 = new SearchResultWrapper();
        w2.setDeparture(1);
        w2.setArrival(2);
        w2.setOverMidnightArrival(false);
        w2.setTravelTime(1);
        w2.setNumberOfTransfers(0);

        List<Long> stops2 = new ArrayList<>();
        stops2.add(7l);
        stops2.add(8l);
        w2.setStops(stops2);


        List<SearchResultWrapper> searchResultWrappers = new ArrayList<>();
        searchResultWrappers.add(w1);
        searchResultWrappers.add(w2);
        return searchResultWrappers;
    }

}