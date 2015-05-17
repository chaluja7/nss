package cz.cvut.nss.services;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.enums.LineType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Line service tests.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class LineServiceTest extends AbstractServiceTest {

    @Autowired
    private LineService lineService;

    @Autowired
    private RouteService routeService;

    @Test
    public void testCreateAndGet() {
        Line line = prepareLine();

        Line retrieved = lineService.getLine(line.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getRoute());
        Assert.assertEquals(line.getName(), retrieved.getName());
        Assert.assertEquals(line.getLineType(), retrieved.getLineType());
    }

    @Test
    public void testUpdate() {
        Line line = prepareLine();

        Line retrieved = lineService.getLine(line.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setName("newName");
        retrieved.setLineType(LineType.BUS);
        lineService.updateLine(retrieved);

        Line updated = lineService.getLine(line.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getName(), updated.getName());
        Assert.assertEquals(retrieved.getLineType(), updated.getLineType());
    }

    @Test
    public void testDelete() {
        Line line = prepareLine();

        Line retrieved = lineService.getLine(line.getId());
        Assert.assertNotNull(retrieved);

        lineService.deleteLine(line.getId());
        Assert.assertNull(lineService.getLine(line.getId()));
    }

    private Line prepareLine() {
        Route route = new Route();
        route.setName("testRoute");
        routeService.createRoute(route);

        Line line = new Line();
        line.setName("testLine");
        line.setLineType(LineType.TRAIN);
        line.setRoute(route);

        lineService.createLine(line);
        return line;
    }

}
