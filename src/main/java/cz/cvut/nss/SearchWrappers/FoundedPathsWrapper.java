package cz.cvut.nss.SearchWrappers;

import cz.cvut.nss.entities.Stop;

import java.util.List;

/**
 * Wrapper to wrap founded paths.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
public class FoundedPathsWrapper {

    private String travelTime;

    private List<Stop> stops;

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

}
