package cz.cvut.nss.services;

import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.RouteStop;
import cz.cvut.nss.entities.Station;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RouteStop service test.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class RouteStopServiceTest extends AbstractServiceTest {

    @Autowired
    private RouteStopService routeStopService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @Test
    public void testCreateAndGet() {
        RouteStop routeStop = prepareRouteStop();

        RouteStop retrieved = routeStopService.getRouteStop(routeStop.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getRoute());
        Assert.assertNotNull(retrieved.getStation());
        Assert.assertEquals(routeStop.getDistance(), retrieved.getDistance());
    }

    @Test
    public void testUpdate() {
        RouteStop routeStop = prepareRouteStop();

        RouteStop retrieved = routeStopService.getRouteStop(routeStop.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setDistance(200);
        routeStopService.updateRouteStop(retrieved);

        RouteStop updated = routeStopService.getRouteStop(routeStop.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getDistance(), updated.getDistance());
    }

    @Test
    public void testDelete() {
        RouteStop routeStop = prepareRouteStop();

        RouteStop retrieved = routeStopService.getRouteStop(routeStop.getId());
        Assert.assertNotNull(retrieved);

        routeStopService.deleteRouteStop(routeStop.getId());
        Assert.assertNull(routeStopService.getRouteStop(routeStop.getId()));
    }

    private RouteStop prepareRouteStop() {
        Route route = new Route();
        route.setName("testRoute");
        routeService.createRoute(route);

        Station station = new Station();
        station.setTitle("testStationTitle");
        station.setName("testStationName");
        stationService.createStation(station);

        RouteStop routeStop = new RouteStop();
        routeStop.setDistance(100);
        routeStop.setRoute(route);
        routeStop.setStation(station);

        routeStopService.createRouteStop(routeStop);
        return routeStop;
    }

}
