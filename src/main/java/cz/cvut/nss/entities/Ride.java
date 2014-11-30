package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * One ride of a Line.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "rides")
public class Ride extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "line_id")
    @Index(name = "ride_line_index")
    private Line line;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ride")
    @OrderBy("departure ASC")
    private List<Stop> stops;

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public List<Stop> getStops() {
        if(stops == null) {
            stops = new ArrayList<>();
        }

        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void addStop(Stop stop) {
        if(!getStops().contains(stop)) {
            stops.add(stop);
        }

        stop.setRide(this);
    }

}
