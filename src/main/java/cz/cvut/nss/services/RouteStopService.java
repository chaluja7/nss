package cz.cvut.nss.services;

import cz.cvut.nss.entities.RouteStop;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Common interface for all RouteStopService implementations.
 */
public interface RouteStopService {

    /**
     * find routeStop by id
     * @param id id of a routeStop
     * @return routeStop by id or null
     */
    public RouteStop getRouteStop(long id);

    /**
     * update routeStop
     * @param routeStop routeStop to update
     * @return updated routeStop
     */
    public RouteStop updateRouteStop(RouteStop routeStop);

    /**
     * persists routeStop
     * @param routeStop routeStop to persist
     */
    public void createRouteStop(RouteStop routeStop);

    /**
     * delete routeStop
     * @param id id of routeStop to delete
     */
    public void deleteRouteStop(long id);

    /**
     * find all routeStops
     * @return all routeStops
     */
    public List<RouteStop> getAll();

}
