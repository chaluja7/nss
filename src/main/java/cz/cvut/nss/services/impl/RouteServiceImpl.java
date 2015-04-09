package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.RouteDao;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.services.RouteService;
import cz.cvut.nss.services.neo4j.StopNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of RouteService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    protected RouteDao routeDao;

    @Autowired
    protected StopNeo4jService stopNeo4jService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Route getRoute(long id) {
        return routeDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Route getRouteLazyInitialized(long id) {
        Route route = routeDao.find(id);
        if(route != null) {
            route.getRouteStops().size();
        }

        return route;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Route updateRoute(Route route) {
        return routeDao.update(route);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createRoute(Route route) {
        routeDao.create(route);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteRoute(long id) {
        Route route = routeDao.find(id);
        if(route != null) {
            for(Line line : route.getLines()) {
                for(Ride ride : line.getRides()) {
                    stopNeo4jService.deleteAllByRideId(ride.getId());
                }
            }

            routeDao.delete(id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Route> getAll() {
        return routeDao.findAll();
    }

}
