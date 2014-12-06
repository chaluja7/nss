package cz.cvut.nss.SearchWrappers;

import java.util.List;

/**
 * Wrapper pro zobrazeni vysledku vyhledavani cesty.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public class SearchResultWrapper {

    private long travelTime;

    private long arrival;

    private List<Long> stops;

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    public long getArrival() {
        return arrival;
    }

    public void setArrival(long arrival) {
        this.arrival = arrival;
    }

    public List<Long> getStops() {
        return stops;
    }

    public void setStops(List<Long> stops) {
        this.stops = stops;
    }
}
