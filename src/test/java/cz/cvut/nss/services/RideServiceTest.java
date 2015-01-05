package cz.cvut.nss.services;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.enums.LineType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Ride service test.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class RideServiceTest extends AbstractServiceTest {

    @Autowired
    private RideService rideService;

    @Autowired
    private LineService lineService;

    @Autowired
    private RouteService routeService;

    @Test
    public void testCreateAndGet() {
        Ride ride = prepareRide();

        Ride retrieved = rideService.getRide(ride.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getLine());
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

        Ride ride = new Ride();
        ride.setLine(line);

        rideService.createRide(ride);
        return ride;
    }

}
