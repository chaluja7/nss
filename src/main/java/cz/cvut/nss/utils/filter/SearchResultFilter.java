package cz.cvut.nss.utils.filter;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Trida pro filtrovani vysledku vyhledavani spoju.
 *
 * @author jakubchalupa
 * @since 31.03.15
 */
public class SearchResultFilter {

    /**
     * vyfiltruje jen relevantni search resulty
     * @param resultList vsechny vysledky vyhledavani - SERAZENE!
     * @return list vyfiltrovanych nejlepsich vysledku
     */
    public static List<SearchResultWrapper> getFilteredResults(List<SearchResultWrapper> resultList) {
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
