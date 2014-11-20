package cz.cvut.nss.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakubchalupa on 19.11.14.
 *
 * Geographical region.
 */
@Entity
@Table(name = "regions")
public class Region extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private List<Station> stations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        if(stations == null) {
            stations = new ArrayList<>();
        }

        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

}
