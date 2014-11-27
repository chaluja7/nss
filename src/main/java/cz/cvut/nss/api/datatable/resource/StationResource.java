package cz.cvut.nss.api.datatable.resource;

/**
 * Resource stations.
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
public class StationResource {

    private Long id;

    private String title;

    private String name;

    private String region;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
