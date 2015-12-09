package cz.cvut.nss.utils.comparator;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Search results comparator tests.
 *
 * @author jakubchalupa
 * @since 16.05.15
 */
public class SearchResultByDepartureDateComparatorTest {

    @Test
    public void testCompareByOverMidnightArrival() {

        SearchResultWrapper w1 = getWrapperForTest();
        SearchResultWrapper w2 = getWrapperForTest();

        List<SearchResultWrapper> wrappers = new ArrayList<>();
        wrappers.add(w1);
        wrappers.add(w2);

        Assert.assertTrue(wrappers.get(0) == w1);
        Assert.assertTrue(wrappers.get(1) == w2);

        w1.setOverMidnightArrival(true);
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());
        Assert.assertTrue(wrappers.get(0) == w2);
        Assert.assertTrue(wrappers.get(1) == w1);
    }

    @Test
    public void testCompareByArrival() {
        SearchResultWrapper w1 = getWrapperForTest();
        SearchResultWrapper w2 = getWrapperForTest();

        List<SearchResultWrapper> wrappers = new ArrayList<>();
        wrappers.add(w1);
        wrappers.add(w2);

        Assert.assertTrue(wrappers.get(0) == w1);
        Assert.assertTrue(wrappers.get(1) == w2);

        w1.setArrival(5);
        w1.setTravelTime(4);
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());
        Assert.assertTrue(wrappers.get(0) == w2);
        Assert.assertTrue(wrappers.get(1) == w1);
    }

    @Test
    public void testCompareByDeparture() {
        SearchResultWrapper w1 = getWrapperForTest();
        SearchResultWrapper w2 = getWrapperForTest();

        List<SearchResultWrapper> wrappers = new ArrayList<>();
        wrappers.add(w1);
        wrappers.add(w2);

        Assert.assertTrue(wrappers.get(0) == w1);
        Assert.assertTrue(wrappers.get(1) == w2);

        w1.setDeparture(0);
        w1.setTravelTime(2);
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());
        Assert.assertTrue(wrappers.get(0) == w2);
        Assert.assertTrue(wrappers.get(1) == w1);
    }

    @Test
    public void testCompareByNumberOfTransfers() {
        SearchResultWrapper w1 = getWrapperForTest();
        SearchResultWrapper w2 = getWrapperForTest();

        List<SearchResultWrapper> wrappers = new ArrayList<>();
        wrappers.add(w1);
        wrappers.add(w2);

        Assert.assertTrue(wrappers.get(0) == w1);
        Assert.assertTrue(wrappers.get(1) == w2);

        w1.setNumberOfTransfers(3);
        Collections.sort(wrappers, new SearchResultByDepartureDateComparator());
        Assert.assertTrue(wrappers.get(0) == w2);
        Assert.assertTrue(wrappers.get(1) == w1);
    }

    private SearchResultWrapper getWrapperForTest() {
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

        return w1;
    }


}
