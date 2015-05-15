package cz.cvut.nss.SearchWrappers;

import java.util.List;

/**
 * Wrapper to wrap search results.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public class SearchResultWrapper {

    private long travelTime;

    private long departure;

    private long arrival;

    private boolean overMidnightArrival;

    private int numberOfTransfers;

    private List<Long> stops;

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    public long getDeparture() {
        return departure;
    }

    public void setDeparture(long departure) {
        this.departure = departure;
    }

    public long getArrival() {
        return arrival;
    }

    public void setArrival(long arrival) {
        this.arrival = arrival;
    }

    public boolean isOverMidnightArrival() {
        return overMidnightArrival;
    }

    public void setOverMidnightArrival(boolean overMidnightArrival) {
        this.overMidnightArrival = overMidnightArrival;
    }

    public int getNumberOfTransfers() {
        return numberOfTransfers;
    }

    public void setNumberOfTransfers(int numberOfTransfers) {
        this.numberOfTransfers = numberOfTransfers;
    }

    public List<Long> getStops() {
        return stops;
    }

    public void setStops(List<Long> stops) {
        this.stops = stops;
    }

}
