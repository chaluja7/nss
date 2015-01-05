package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.RouteStopDao;
import cz.cvut.nss.entities.RouteStop;
import cz.cvut.nss.services.RouteStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of RouteStopService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class RouteStopServiceImpl implements RouteStopService {

    @Autowired
    protected RouteStopDao routeStopDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public RouteStop getRouteStop(long id) {
        return routeStopDao.find(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public RouteStop updateRouteStop(RouteStop routeStop) {
        return routeStopDao.update(routeStop);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createRouteStop(RouteStop routeStop) {
        routeStopDao.create(routeStop);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteRouteStop(long id) {
        routeStopDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<RouteStop> getAll() {
        return routeStopDao.findAll();
    }

}
