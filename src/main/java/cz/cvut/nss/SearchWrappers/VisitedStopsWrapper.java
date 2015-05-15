package cz.cvut.nss.SearchWrappers;

/**
 * @author jakubchalupa
 * @since 07.05.15
 */
public class VisitedStopsWrapper {

    private long stopId;

    private long departureMillis;

    public VisitedStopsWrapper() {

    }

    public VisitedStopsWrapper(long stopId, long departureMillis) {
        this.stopId = stopId;
        this.departureMillis = departureMillis;
    }

    public long getStopId() {
        return stopId;
    }

    public void setStopId(long stopId) {
        this.stopId = stopId;
    }

    public long getDepartureMillis() {
        return departureMillis;
    }

    public void setDepartureMillis(long departureMillis) {
        this.departureMillis = departureMillis;
    }

    public VisitedStopsWrapper getDeepCopy() {
        return new VisitedStopsWrapper(new Long(stopId), new Long(departureMillis));
    }
}
