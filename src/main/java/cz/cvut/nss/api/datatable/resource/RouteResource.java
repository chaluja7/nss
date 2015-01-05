package cz.cvut.nss.api.datatable.resource;

/**
 * Route resource.
 *
 * @author jakubchalupa
 * @since 29.11.14
 */
public class RouteResource {

    private long id;

    private String name;

    private String stationFrom;

    private String stationTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
