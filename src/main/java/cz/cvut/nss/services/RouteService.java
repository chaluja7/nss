package cz.cvut.nss.services;

import cz.cvut.nss.entities.Route;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Common interface for all RouteService implementations.
 */
public interface RouteService {

    /**
     * find route by id
     * @param id id of a route
     * @return route by id or null
     */
    public Route getRoute(long id);

    /**
     * update route
     * @param route route to update
     * @return updated route
     */
    public Route updateRoute(Route route);

    /**
     * persists route
     * @param route route to persist
     */
    public void createRoute(Route route);

    /**
     * delete route
     * @param id id of route to delete
     */
    void deleteRoute(long id);

    /**
     * find all routes
     * @return all routes
     */
    public List<Route> getAll();

}
