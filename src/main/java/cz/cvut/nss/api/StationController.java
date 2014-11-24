package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jakubchalupa on 24.11.14.
 *
 * Controller for station resource.
 */
@RestController
public class StationController {

    @Autowired
    protected StationService stationService;

    @RequestMapping(value ="/stations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<Station> getStations() {
        return stationService.getAll();
    }

    @RequestMapping(value ="/stationsDataTable", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public DataTableResource<Station> getStationsForDataTable() {
        return new DataTableResource<>(stationService.getAll());
    }

}
