package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.Region;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.RegionService;
import cz.cvut.nss.services.StationService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Backing bean for station.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@ManagedBean
@ViewScoped
public class StationBB {

    @ManagedProperty(value = "#{stationServiceImpl}")
    private StationService stationService;

    @ManagedProperty(value = "#{regionServiceImpl}")
    private RegionService regionService;

    private Long id;

    private Station station = new Station();

    public void loadStation() {
        if(id != null) {
            station = stationService.getStationLazyInitialized(id);
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

    public Map<String, Object> getAllRegions() {
        Map<String, Object> map = new HashMap<>();
        for(Region region : regionService.getAll()) {
            map.put(region.getName(), region);
        }

        return map;
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

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

}
