package cz.cvut.nss.utils.evaluator;

import java.util.*;

/**
 * @author jakubchalupa
 * @since 20.04.15
 */
public class StationRideWrapper {

    private Set<Long> visitedRides;

    private Map<Long, List<Long>> visitedStations;

    public Set<Long> getVisitedRides() {
        if(visitedRides == null) {
            visitedRides = new HashSet<>();
        }

        return visitedRides;
    }

    public void setVisitedRides(Set<Long> visitedRides) {
        this.visitedRides = visitedRides;
    }

    public Map<Long, List<Long>> getVisitedStations() {
        if(visitedStations == null) {
            visitedStations = new HashMap<>();
        }

        return visitedStations;
    }

    public void setVisitedStations(Map<Long, List<Long>> visitedStations) {
        this.visitedStations = visitedStations;
    }
}
