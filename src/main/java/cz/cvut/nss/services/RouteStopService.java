package cz.cvut.nss.services;

import cz.cvut.nss.entities.RouteStop;

import java.util.List;

/**
 * Common interface for all RouteStopService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface RouteStopService {

    /**
     * find routeStop by id
     * @param id id of a routeStop
     * @return routeStop by id or null
     */
    RouteStop getRouteStop(long id);

    /**
     * update routeStop
     * @param routeStop routeStop to update
     * @return updated routeStop
     */
    RouteStop updateRouteStop(RouteStop routeStop);

    /**
     * persists routeStop
     * @param routeStop routeStop to persist
     */
    void createRouteStop(RouteStop routeStop);

    /**
     * delete routeStop
     * @param id id of routeStop to delete
     */
    void deleteRouteStop(long id);

    /**
     * find all routeStops
     * @return all routeStops
     */
    List<RouteStop> getAll();

}
