package cz.cvut.nss.utils.comparator;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;

import java.util.Comparator;

/**
 * Slouzi pro serazeni vysledku vyhledavani spojeni dle data odjezdu.
 *
 * @author jakubchalupa
 * @since 12.03.15
 */
public class SearchResultByDepartureDateComparator implements Comparator<SearchResultWrapper> {
    @Override
    public int compare(SearchResultWrapper o1, SearchResultWrapper o2) {
        if(o1.getArrival() < o2.getArrival()) {
            return -1;
        }

        if(o1.getArrival() > o2.getArrival()) {
            return 1;
        }

        if(o1.getTravelTime() < o2.getTravelTime()) {
            return -1;
        }

        if(o1.getTravelTime() > o2.getTravelTime()) {
            return 1;
        }

        if(o1.getNumberOfTransfers() < o2.getNumberOfTransfers()) {
            return -1;
        }

        if(o1.getNumberOfTransfers() > o2.getNumberOfTransfers()) {
            return 1;
        }

        return 0;
    }
}
