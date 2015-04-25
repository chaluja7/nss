package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.RouteResource;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.RouteStop;
import cz.cvut.nss.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for route resource.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@RestController
public class RouteController {

    @Autowired
    protected RouteService routeService;

    @RequestMapping(value ="/routes", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public List<RouteResource> getRoutes() {
        return getTransformedRoutes(routeService.getAll());
    }

    @RequestMapping(value ="/routesDataTable", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public DataTableResource<RouteResource> getRoutesForDataTable() {
        return new DataTableResource<>(getTransformedRoutes(routeService.getAll()));
    }

    /**
     * ztransformuje route entity na route resourcy
     *
     * @return list route resourcu
     */
    private List<RouteResource> getTransformedRoutes(List<Route> routeList) {
        List<RouteResource> resourceList = new ArrayList<>();
        for(Route route : routeList) {
            RouteResource resource = new RouteResource();
            resource.setId(route.getId());
            resource.setName(route.getName());

            //RouteStops jsou seřazeny podle km. 1. v listu je tedy stationFrom, poslední stationTo
            List<RouteStop> routeStops = route.getRouteStops();
            if(routeStops.size() > 0) {
                resource.setStationFrom(routeStops.get(0).getStation().getTitle());
                resource.setStationTo(routeStops.get(routeStops.size() - 1).getStation().getTitle());
            }

            resourceList.add(resource);
        }

        return resourceList;
    }

}
