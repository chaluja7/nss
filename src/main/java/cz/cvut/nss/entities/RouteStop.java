package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;

import javax.persistence.*;

/**
 * One stop on Route.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "route_stops")
public class RouteStop extends AbstractEntity {

    @Column
    private Integer distance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id")
    @Index(name = "route_stop_station_index")
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id")
    @Index(name = "route_stop_route_index")
    private Route route;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
