package cz.cvut.nss.api.datatable.resource;

/**
 * Ride resource.
 *
 * @author jakubchalupa
 * @since 29.11.14
 */
public class RideResource {

    private long id;

    private String line;

    private String departure;

    private String arrival;

    private String stationFrom;

    private String stationTo;

    private String duration;

    private String operationInterval;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOperationInterval() {
        return operationInterval;
    }

    public void setOperationInterval(String operationInterval) {
        this.operationInterval = operationInterval;
    }
}
