package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by jakubchalupa on 21.11.14.
 *
 * Backing bean for station.
 */
@Controller
@Scope(value = "request")
public class StationBB {

    private Station station = new Station();

    @Autowired
    private StationService stationService;

    public String saveStation() {
        stationService.createStation(station);
        return "station-list";
    }

    public List<Station> getStations() {
        return stationService.getAll();
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

}
