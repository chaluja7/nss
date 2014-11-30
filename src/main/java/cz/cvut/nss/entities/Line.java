package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Common transport.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "lines")
public class Line extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    @Index(name = "line_type_index")
    @NotNull
    private LineType lineType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "route_id")
    @Index(name = "line_route_index")
    private Route route;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "line")
    private List<Ride> rides;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Ride> getRides() {
        if(rides == null) {
            rides = new ArrayList<>();
        }

        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public void addRide(Ride ride) {
        if(!getRides().contains(ride)) {
            rides.add(ride);
        }

        ride.setLine(this);
    }
}
