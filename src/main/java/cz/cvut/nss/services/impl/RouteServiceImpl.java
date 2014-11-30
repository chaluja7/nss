package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.RouteDao;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Route updateRoute(Route route) {
        return routeDao.update(route);
    }

    @Override
    @Transactional
    public void createRoute(Route route) {
        routeDao.create(route);
    }

    @Override
    @Transactional
    public void deleteRoute(long id) {
        routeDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Route> getAll() {
        return routeDao.findAll();
    }

}
