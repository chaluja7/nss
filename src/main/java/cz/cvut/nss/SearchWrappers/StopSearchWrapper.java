package cz.cvut.nss.SearchWrappers;

import java.sql.Time;

/**
 * Wrapper to wrap founded stops.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public class StopSearchWrapper {

    private Long id;

    private Long stationId;

    private Long rideId;

    private Long stopOrder;

    private Time departure;

    private Time arrival;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Long getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Long stopOrder) {
        this.stopOrder = stopOrder;
    }

    public Time getDeparture() {
        return departure;
    }

    public void setDeparture(Time departure) {
        this.departure = departure;
    }

    public Time getArrival() {
        return arrival;
    }

    public void setArrival(Time arrival) {
        this.arrival = arrival;
    }
}
