package cz.cvut.nss.services;

import cz.cvut.nss.entities.*;
import cz.cvut.nss.entities.enums.LineType;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Stop service test.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class StopServiceTest extends AbstractServiceTest {

    @Autowired
    private StopService stopService;

    @Autowired
    private RideService rideService;

    @Autowired
    private LineService lineService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @Test
    public void testCreateAndGet() {
        Stop stop = prepareStop();

        Stop retrieved = stopService.getStop(stop.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getRide());
        Assert.assertNotNull(retrieved.getStation());
        Assert.assertEquals(stop.getArrival().getDayOfYear(), retrieved.getArrival().getDayOfYear());
    }

    @Test
    public void testUpdate() {
        Stop stop = prepareStop();

        Stop retrieved = stopService.getStop(stop.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setArrival(new LocalDateTime().plusDays(1));
        stopService.updateStop(retrieved);

        Stop updated = stopService.getStop(stop.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getArrival().getDayOfYear(), updated.getArrival().getDayOfYear());
    }

    @Test
    public void testDelete() {
        Stop stop = prepareStop();

        Stop retrieved = stopService.getStop(stop.getId());
        Assert.assertNotNull(retrieved);

        stopService.deleteStop(stop.getId());
        Assert.assertNull(stopService.getStop(stop.getId()));
    }

    private Stop prepareStop() {
        Route route = new Route();
        route.setName("testRouteName");
        routeService.createRoute(route);

        Line line = new Line();
        line.setName("testLineName");
        line.setLineType(LineType.TRAIN);
        line.setRoute(route);
        lineService.createLine(line);

        Ride ride = new Ride();
        ride.setLine(line);
        rideService.createRide(ride);

        Station station = new Station();
        station.setTitle("testStationTitle");
        station.setName("testStationName");
        stationService.createStation(station);

        Stop stop = new Stop();
        stop.setArrival(new LocalDateTime());
        stop.setRide(ride);
        stop.setStation(station);

        stopService.createStop(stop);
        return stop;
    }

}
