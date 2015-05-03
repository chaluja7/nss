package cz.cvut.nss.utils.filter;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.utils.DateTimeUtils;

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
        SearchResultWrapper prevWrapper = null;
        for(SearchResultWrapper wrapper : resultList) {

            //zkontroluji, jestli minuly vysledek nemel pozdejsi departure nez ten aktualni, pokud ano tak aktualni nechci
            if(prevWrapper != null) {
                //do razeni chci zakomponovat i penalizace za prestup! Toto funguje i pres pulnoc (dostanu se do zapornych cisel ale porovnani je stejne)
                long wrapperDepartureWithPenalty = wrapper.getDeparture() - (DateTimeUtils.TRANSFER_PENALTY_MILLIS * wrapper.getNumberOfTransfers());
                long prevWrapperDepartureWithPenalty = prevWrapper.getDeparture() - (DateTimeUtils.TRANSFER_PENALTY_MILLIS * prevWrapper.getNumberOfTransfers());

                if(wrapperDepartureWithPenalty < prevWrapperDepartureWithPenalty) {
                    continue;
                }
            }

            //zkontroluji, jestli nejaky z nodu v aktualnim wrapperu jiz nebyl pouzit drive
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

            prevWrapper = wrapper;
        }

        return finalList;
    }

}
