package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.RideDao;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import cz.cvut.nss.entities.neo4j.RideNode;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.services.neo4j.OperationIntervalNeo4jService;
import cz.cvut.nss.services.neo4j.RideNeo4jService;
import cz.cvut.nss.services.neo4j.StopNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of RideService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class RideServiceImpl implements RideService {

    @Autowired
    protected RideDao rideDao;

    @Autowired
    protected StopNeo4jService stopNeo4jService;

    @Autowired
    protected RideNeo4jService rideNeo4jService;

    @Autowired
    protected OperationIntervalNeo4jService operationIntervalNeo4jService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Ride getRide(long id) {
        return rideDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Ride getRideLazyInitialized(long id) {
        Ride ride = rideDao.find(id);
        if(ride != null) {
            ride.getStops().size();
        }

        return ride;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Ride updateRide(Ride ride) {
        return rideDao.update(ride);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createRide(Ride ride) {
        rideDao.create(ride);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteRide(long id) {
        rideDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Ride> getAll() {
        return rideDao.findAll();
    }

    @Override
    @Transactional
    public void importRideToNeo4j(Ride ride) {

        if(ride != null) {
            OperationInterval operationInterval = ride.getOperationInterval();
            if(operationInterval == null) {
                throw new RuntimeException();
            }

            OperationIntervalNode operationIntervalNode = operationIntervalNeo4jService.findByOperationIntervalId(operationInterval.getId());
            RideNode rideNode = new RideNode();
            rideNode.setRideId(ride.getId());
            rideNode.setOperationIntervalNode(operationIntervalNode);
            rideNeo4jService.save(rideNode);

            List<Stop> stopList = ride.getStops();
            StopNode prevStopNode = null;
            for (Stop stop : stopList) {
                StopNode stopNode = new StopNode();
                stopNode.setStopId(stop.getId());
                stopNode.setStationId(stop.getStation().getId());
                stopNode.setRideId(stop.getRide().getId());
                stopNode.setRideNode(rideNode);
                if (stop.getArrival() != null) {
                    //TODO?
                    stopNode.setArrivalInMillis((long) stop.getArrival().getMillisOfDay());
                   //stopNode.setArrivalInMillis(stop.getArrival().toDateTime().getMillis());
                }
                if (stop.getDeparture() != null) {
                    //TODO?
                    stopNode.setDepartureInMillis((long) stop.getDeparture().getMillisOfDay());
                    //stopNode.setDepartureInMillis(stop.getDeparture().toDateTime().getMillis());
                }

                if (prevStopNode != null) {
                    stopNode.hasPrevStopNodeRelationShip(prevStopNode, stopNode.getArrivalInMillis() - prevStopNode.getDepartureInMillis());
                }

                prevStopNode = stopNeo4jService.save(stopNode);
            }
        }
    }

}
