package cz.cvut.nss.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakubchalupa on 19.11.14.
 *
 * A path of a Line.
 */
@Entity
@Table(name = "routes")
public class Route extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "route")
    private List<RouteStop> routeStops;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "route")
    private List<Line> lines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RouteStop> getRouteStops() {
        if(routeStops == null) {
            routeStops = new ArrayList<>();
        }

        return routeStops;
    }

    public void setRouteStops(List<RouteStop> routeStops) {
        this.routeStops = routeStops;
    }

    public void addRouteStop(RouteStop routeStop) {
        if(!getRouteStops().contains(routeStop)) {
            routeStops.add(routeStop);
        }

        routeStop.setRoute(this);
    }

    public List<Line> getLines() {
        if(lines == null) {
            lines = new ArrayList<>();
        }

        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void addLine(Line line) {
        if(!getLines().contains(line)) {
            lines.add(line);
        }

        line.setRoute(this);
    }
}
