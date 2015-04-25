package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Station.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "google_trips")
public class GoogleTrip extends AbstractEntity {

    @Column(unique = true)
    private Integer tripId;

    @Column
    @Index(name = "google_trip_route_index")
    private String routeId;

    @Column
    @Index(name = "google_trip_service_index")
    private Integer serviceId;

    @Column
    @Index(name = "google_trip_agency_index")
    private Integer agencyId;

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }
}
