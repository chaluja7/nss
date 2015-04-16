package cz.cvut.nss.entities;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Station.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "google_routes")
public class GoogleRoute extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String routeId;

    @Column
    @NotBlank
    private String name;

    @Column
    @Index(name = "google_route_agency_index")
    private Integer agencyId;

    @Column
    private Integer type;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
