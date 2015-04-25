package cz.cvut.nss.utils.evaluator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jakubchalupa
 * @since 20.04.15
 */
public class StationRideWrapper {

    private Set<Long> visitedRides;

    private Set<Long> visitedStations;

    public Set<Long> getVisitedRides() {
        if(visitedRides == null) {
            visitedRides = new HashSet<>();
        }

        return visitedRides;
    }

    public void setVisitedRides(Set<Long> visitedRides) {
        this.visitedRides = visitedRides;
    }

    public Set<Long> getVisitedStations() {
        if(visitedStations == null) {
            visitedStations = new HashSet<>();
        }

        return visitedStations;
    }

    public void setVisitedStations(Set<Long> visitedStations) {
        this.visitedStations = visitedStations;
    }
}
