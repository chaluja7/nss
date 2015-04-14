package cz.cvut.nss.services.impl;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.services.LineService;
import cz.cvut.nss.services.RouteService;
import cz.cvut.nss.services.SynchronizationService;
import cz.cvut.nss.services.neo4j.StopNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Service
public class SynchronizationServiceImpl implements SynchronizationService {

    @Autowired
    protected RouteService routeService;

    @Autowired
    protected LineService lineService;

    @Autowired
    protected StopNeo4jService stopNeo4jService;

    @Override
    @Transactional
    public void deleteRouteById(long routeId) {
        Route route = routeService.getRoute(routeId);
        if(route != null) {
            for(Line line : route.getLines()) {
                for(Ride ride : line.getRides()) {
                    stopNeo4jService.deleteAllByRideId(ride.getId());
                }
            }

            routeService.deleteRoute(routeId);
        }
    }

    @Override
    @Transactional
    public void deleteLineById(long lineId) {
        Line line = lineService.getLine(lineId);
        if(line != null) {
            for(Ride ride : line.getRides()) {
                stopNeo4jService.deleteAllByRideId(ride.getId());
            }

            lineService.deleteLine(lineId);
        }
    }

}
