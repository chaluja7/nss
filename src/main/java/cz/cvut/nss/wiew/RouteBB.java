package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.RouteStop;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.RouteService;
import cz.cvut.nss.services.RouteStopService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.SynchronizationService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backing bean for route.
 *
 * @author jakubchalupa
 * @since 29.11.14.
 */
@ManagedBean
@ViewScoped
public class RouteBB {

    @ManagedProperty(value = "#{routeServiceImpl}")
    private RouteService routeService;

    @ManagedProperty(value = "#{stationServiceImpl}")
    private StationService stationService;

    @ManagedProperty(value = "#{routeStopServiceImpl}")
    private RouteStopService routeStopService;

    @ManagedProperty(value = "#{synchronizationServiceImpl}")
    private SynchronizationService synchronizationService;

    private Long id;

    private Route route = new Route();

    private Station newRouteStop;

    private Integer newRouteStopDistance;

    public void loadRoute() {
        if(id != null) {
            route = routeService.getRouteLazyInitialized(id);
        }
    }

    public String saveRoute() {
        routeService.createRoute(route);
        return "route-list?faces-redirect=true";
    }

    public String updateRoute() {
        routeService.updateRoute(route);
        return "route-list?faces-redirect=true";
    }

    public String deleteRoute() {
        synchronizationService.deleteRouteById(route.getId());
        return "route-list?faces-redirect=true";
    }

    public String deleteRouteStop(long routeStopId) {
        routeStopService.deleteRouteStop(routeStopId);
        return "route?id=" + id + "&faces-redirect=true";
    }

    public String addRouteStop() {
        if(id == null) {
            routeService.createRoute(route);
            id = route.getId();
        }

        RouteStop routeStop = new RouteStop();
        routeStop.setDistance(newRouteStopDistance);
        routeStop.setRoute(route);
        routeStop.setStation(newRouteStop);

        routeStopService.createRouteStop(routeStop);
        return "route?id=" + id + "&faces-redirect=true";
    }

    public Map<String, Object> getAllAvailableStations() {
        Map<String, Object> map = new HashMap<>();
        for(Station station : stationService.getAll()) {
            if(!usedStations().contains(station)) {
                map.put(station.getTitle(), station);
            }
        }

        return map;
    }

    private List<Station> usedStations() {
        List<Station> stationList = new ArrayList<>();

        if(id == null) {
            return stationList;
        }

        for(RouteStop routeStop : route.getRouteStops()) {
            stationList.add(routeStop.getStation());
        }

        return stationList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Station getNewRouteStop() {
        return newRouteStop;
    }

    public void setNewRouteStop(Station newRouteStop) {
        this.newRouteStop = newRouteStop;
    }

    public Integer getNewRouteStopDistance() {
        return newRouteStopDistance;
    }

    public void setNewRouteStopDistance(Integer newRouteStopDistance) {
        this.newRouteStopDistance = newRouteStopDistance;
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setRouteStopService(RouteStopService routeStopService) {
        this.routeStopService = routeStopService;
    }

    public void setSynchronizationService(SynchronizationService synchronizationService) {
        this.synchronizationService = synchronizationService;
    }
}
