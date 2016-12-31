package cz.cvut.nss.services;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.enums.LineType;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Ride service tests.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class RideServiceIT extends AbstractServiceIT {

    @Autowired
    private RideService rideService;

    @Autowired
    private LineService lineService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private OperationIntervalService operationIntervalService;

    @Test
    public void testCreateAndGet() {
        Ride ride = prepareRide();

        Ride retrieved = rideService.getRide(ride.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getLine());
        Assert.assertNotNull(retrieved.getOperationInterval());
    }

    @Test
    public void testDelete() {
        Ride ride = prepareRide();

        Ride retrieved = rideService.getRide(ride.getId());
        Assert.assertNotNull(retrieved);

        rideService.deleteRide(ride.getId());
        Assert.assertNull(rideService.getRide(ride.getId()));
    }

    private Ride prepareRide() {
        Route route = new Route();
        route.setName("testRoute");
        routeService.createRoute(route);

        Line line = new Line();
        line.setName("testLine");
        line.setLineType(LineType.TRAIN);
        line.setRoute(route);
        lineService.createLine(line);

        OperationInterval operationInterval = new OperationInterval();
        operationInterval.setStartDate(new LocalDate());
        operationInterval.setEndDate(new LocalDate());
        operationIntervalService.createOperationInterval(operationInterval);

        Ride ride = new Ride();
        ride.setLine(line);
        ride.setOperationInterval(operationInterval);

        rideService.createRide(ride);
        return ride;
    }

}
