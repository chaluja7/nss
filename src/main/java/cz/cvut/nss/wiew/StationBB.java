package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Backing bean for station.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@ManagedBean
@ViewScoped
public class StationBB {

    private Long id;

    private Station station = new Station();

    @ManagedProperty(value = "#{stationServiceImpl}")
    private StationService stationService;

    public void loadStation() {
        if(id != null) {
            station = stationService.getStation(id);
        }
    }

    public String saveStation() {
        stationService.createStation(station);
        return "station-list?faces-redirect=true";
    }

    public String updateStation() {
        stationService.updateStation(station);
        return "station-list?faces-redirect=true";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

}
