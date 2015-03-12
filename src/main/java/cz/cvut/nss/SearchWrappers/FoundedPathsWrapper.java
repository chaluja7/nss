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

    private int travelTimeHours;

    private int travelTimeMinutes;

    private List<Stop> stops;

    public int getTravelTimeHours() {
        return travelTimeHours;
    }

    public void setTravelTimeHours(int travelTimeHours) {
        this.travelTimeHours = travelTimeHours;
    }

    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    public void setTravelTimeMinutes(int travelTimeMinutes) {
        this.travelTimeMinutes = travelTimeMinutes;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

}
