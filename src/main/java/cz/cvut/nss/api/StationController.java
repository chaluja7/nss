package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.StationResource;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.utils.dto.EntitiesAndCountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Transactional
    @RequestMapping(value ="/stations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<StationResource> getStations() {
        return getTransformedStations(stationService.getAll());
    }

    @Transactional
    @RequestMapping(value ="/stationsDataTable", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public DataTableResource<StationResource> getStationsForDataTable(@RequestBody CommonRequest filter) {
        EntitiesAndCountResult<Station> allForDatatables = stationService.getAllForDatatables(filter);
        return new DataTableResource<>(getTransformedStations(allForDatatables.getEntities()), allForDatatables.getCount(), stationService.getCountAll());
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
    private List<StationResource> getTransformedStations(List<Station> stationList) {
        List<StationResource> resourceList = new ArrayList<>();
        for(Station station : stationList) {
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
