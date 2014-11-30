package cz.cvut.nss.api.datatable.resource;

/**
 * Line resource
 *
 * @author jakubchalupa
 * @since 29.11.14
 */
public class LineResource {

    private long id;

    private String name;

    private String route;

    private String type;

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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
