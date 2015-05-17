package cz.cvut.nss.services;

import cz.cvut.nss.entities.Route;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Route service tests.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class RouteServiceTest extends AbstractServiceTest {

    @Autowired
    private RouteService routeService;

    @Test
    public void testCreateAndGet() {
        Route route = prepareRoute();

        Route retrieved = routeService.getRoute(route.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(route.getName(), retrieved.getName());
    }

    @Test
    public void testUpdate() {
        Route route = prepareRoute();

        Route retrieved = routeService.getRoute(route.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setName("newName");
        routeService.updateRoute(retrieved);

        Route updated = routeService.getRoute(route.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getName(), updated.getName());
    }

    @Test
    public void testDelete() {
        Route route = prepareRoute();

        Route retrieved = routeService.getRoute(route.getId());
        Assert.assertNotNull(retrieved);

        routeService.deleteRoute(route.getId());
        Assert.assertNull(routeService.getRoute(route.getId()));
    }

    private Route prepareRoute() {
        Route route = new Route();
        route.setName("testName");

        routeService.createRoute(route);
        return route;
    }

}
