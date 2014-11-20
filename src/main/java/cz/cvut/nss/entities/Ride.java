package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakubchalupa on 19.11.14.
 *
 * One ride of a Line.
 */
@Entity
@Table(name = "rides")
public class Ride extends AbstractEntity {

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime departure;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "line_id")
    @Index(name = "ride_line_index")
    private Line line;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ride")
    private List<Stop> stops;

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

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
