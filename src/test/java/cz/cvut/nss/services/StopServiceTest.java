package cz.cvut.nss.services;

import cz.cvut.nss.entities.*;
import cz.cvut.nss.entities.enums.LineType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
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

    @Autowired
    private OperationIntervalService operationIntervalService;

    @Test
    public void testCreateAndGet() {
        Stop stop = prepareStop();

        Stop retrieved = stopService.getStop(stop.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getRide());
        Assert.assertNotNull(retrieved.getStation());
        Assert.assertEquals(stop.getArrival().getHourOfDay(), retrieved.getArrival().getHourOfDay());
    }

    @Test
    public void testUpdate() {
        Stop stop = prepareStop();

        Stop retrieved = stopService.getStop(stop.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setArrival(new LocalTime().plusHours(1));
        stopService.updateStop(retrieved);

        Stop updated = stopService.getStop(stop.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getArrival().getHourOfDay(), updated.getArrival().getHourOfDay());
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

        OperationInterval operationInterval = new OperationInterval();
        operationInterval.setStartDate(new LocalDate());
        operationInterval.setEndDate(new LocalDate());
        operationInterval.setMonday(true);
        operationInterval.setTuesday(true);
        operationInterval.setWednesday(true);
        operationInterval.setThursday(true);
        operationInterval.setFriday(true);
        operationInterval.setSaturday(false);
        operationInterval.setSunday(false);
        operationIntervalService.createOperationInterval(operationInterval);

        Ride ride = new Ride();
        ride.setLine(line);
        ride.setOperationInterval(operationInterval);
        rideService.createRide(ride);

        Station station = new Station();
        station.setTitle("testStationTitle");
        station.setName("testStationName");
        stationService.createStation(station);

        Stop stop = new Stop();
        stop.setArrival(new LocalTime());
        stop.setDeparture(new LocalTime());
        stop.setRide(ride);
        stop.setStation(station);

        stopService.createStop(stop);
        return stop;
    }

}
