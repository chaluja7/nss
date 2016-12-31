package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.RouteResource;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.services.RouteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 30.12.16
 */
public class RouteControllerTest {

    private RouteController routeController;

    private RouteService routeServiceMock;

    @Before
    public void setUp() throws Exception {
        routeController = new RouteController();

        routeServiceMock = Mockito.mock(RouteService.class);
        routeController.routeService = routeServiceMock;
    }

    @Test
    public void testGetRoutes() throws Exception {
        Mockito.when(routeServiceMock.getAll()).thenReturn(getRoutes());

        List<RouteResource> routes = routeController.getRoutes();
        Assert.assertNotNull(routes);
        Assert.assertEquals(2, routes.size());

        for(RouteResource route : routes) {
            Assert.assertNotNull(route);
            Assert.assertNotNull(route.getId());
            Assert.assertNotNull(route.getName());
        }
    }

    @Test
    public void testGetRoutesForDataTable() throws Exception {
        Mockito.when(routeServiceMock.getAll()).thenReturn(getRoutes());

        DataTableResource<RouteResource> routeResourceForDataTable = routeController.getRoutesForDataTable();
        Assert.assertNotNull(routeResourceForDataTable);
        Assert.assertNotNull(routeResourceForDataTable.getAaData());
        Assert.assertEquals(2, routeResourceForDataTable.getAaData().size());
    }

    public static List<Route> getRoutes() {
        List<Route> list = new ArrayList<>();
        list.add(getRoute(1L, "route1"));
        list.add(getRoute(2L, "route2"));

        return list;
    }

    public static Route getRoute(Long id, String name) {
        Route route = new Route();
        route.setId(id);
        route.setName(name);

        return route;
    }

}