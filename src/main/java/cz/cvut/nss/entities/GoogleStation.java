package cz.cvut.nss.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Station.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "google_stations")
public class GoogleStation extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String stationId;

    @Column
    @NotBlank
    private String name;

    @Column
    @Min(-90)
    @Max(90)
    private Double latitude;

    @Column
    @Min(-180)
    @Max(180)
    private Double longtitude;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_station")
    private GoogleStation parentStation;

    @OneToMany(mappedBy = "parentStation")
    private List<GoogleStation> childStations;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public GoogleStation getParentStation() {
        return parentStation;
    }

    public void setParentStation(GoogleStation parentStation) {
        this.parentStation = parentStation;
    }

    public List<GoogleStation> getChildStations() {
        if(childStations == null) {
            childStations = new ArrayList<>();
        }
        return childStations;
    }

    public void setChildStations(List<GoogleStation> childStations) {
        this.childStations = childStations;
    }

    public void addChildStation(GoogleStation googleStation) {
        if(!getChildStations().contains(googleStation)) {
            getChildStations().add(googleStation);
        }

        googleStation.setParentStation(this);
    }
}
