package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.StationResource;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for station resource.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@RestController
public class StationController {

    @Autowired
    protected StationService stationService;

    @RequestMapping(value ="/stations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public List<StationResource> getStations() {
        return getAllTransformedStations();
    }

    @RequestMapping(value ="/stationsDataTable", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public DataTableResource<StationResource> getStationsForDataTable() {
        return new DataTableResource<>(getAllTransformedStations());
    }

    @RequestMapping(value ="/stationsTitleByPattern", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<String> getStationsTitleByPattern(@RequestParam("pattern") String pattern) {
        List<Station> stationList = stationService.getAllByNamePattern(pattern);

        List<String> stationTitleList = new ArrayList<>();
        for(Station station : stationList) {
            stationTitleList.add(station.getTitle());
        }

        return stationTitleList;
    }

    /**
     * ztransformuje station entity na station resourcy
     *
     * @return list station resourcu
     */
    private List<StationResource> getAllTransformedStations() {
        List<StationResource> resourceList = new ArrayList<>();
        for(Station station : stationService.getAll()) {
            StationResource resource = new StationResource();
            resource.setId(station.getId());
            resource.setTitle(station.getTitle());
            resource.setName(station.getName());
            resource.setRegion(station.getRegion().getName());

            resourceList.add(resource);
        }

        return resourceList;
    }

}
