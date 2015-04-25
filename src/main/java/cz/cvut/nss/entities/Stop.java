package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * One stop of a ride.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "stops")
public class Stop extends AbstractEntity {

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @Index(name = "stop_arrival_index")
    private LocalTime arrival;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @Index(name = "stop_departure_index")
    private LocalTime departure;

    @Column
    private Integer stopOrder;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "station_id")
    @Index(name = "stop_station_index")
    private Station station;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ride_id")
    @Index(name = "stop_ride_index")
    private Ride ride;

    public LocalTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

}
