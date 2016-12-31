package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.LineResource;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.entities.enums.LineType;
import cz.cvut.nss.services.LineService;
import cz.cvut.nss.utils.dto.EntitiesAndCountResult;
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
public class LineControllerTest {

    private LineController lineController;

    private LineService lineServiceMock;

    @Before
    public void setUp() throws Exception {
        lineController = new LineController();

        lineServiceMock = Mockito.mock(LineService.class);
        lineController.lineService = lineServiceMock;
    }

    /**
     * otestovani metody controleru. Servisa se mockuje.
     * @throws Exception
     */
    @Test
    public void testGetLines() throws Exception {
        Mockito.when(lineServiceMock.getAll()).thenReturn(getLines());

        List<LineResource> lines = lineController.getLines();
        Assert.assertNotNull(lines);
        Assert.assertEquals(2, lines.size());

        for(LineResource line : lines) {
            Assert.assertNotNull(line);
            Assert.assertNotNull(line.getId());
            Assert.assertNotNull(line.getName());
            Assert.assertNotNull(line.getType());
            Assert.assertNotNull(line.getRoute());
        }
    }

    /**
     * otestovani metody controleru. Servisa se mockuje.
     * @throws Exception
     */
    @Test
    public void testGetLinesForDataTable() throws Exception {
        EntitiesAndCountResult<Line> result = new EntitiesAndCountResult<>();
        result.setEntities(getLines());
        final int currentSize = result.getEntities().size();
        result.setCount(currentSize);

        final int countAll = 4;

        Mockito.when(lineServiceMock.getAllForDatatables(Mockito.any(CommonRequest.class))).thenReturn(result);
        Mockito.when(lineServiceMock.getCountAll()).thenReturn(countAll);

        DataTableResource<LineResource> linesForDataTable = lineController.getLinesForDataTable(new CommonRequest());
        Assert.assertNotNull(linesForDataTable);
        Assert.assertNotNull(linesForDataTable.getAaData());
        Assert.assertEquals(currentSize, linesForDataTable.getAaData().size());

        Assert.assertEquals(currentSize, linesForDataTable.getRecordsFiltered());
        Assert.assertEquals(countAll, linesForDataTable.getRecordsTotal());
    }

    public static List<Line> getLines() {
        List<Line> list = new ArrayList<>();

        list.add(getLine(1L, "line1", LineType.BUS, RouteControllerTest.getRoute(1L, "route1")));
        list.add(getLine(2L, "line2", LineType.METRO, RouteControllerTest.getRoute(2L, "route2")));

        return list;
    }

    public static Line getLine(Long id, String name, LineType lineType, Route route) {
        Line line = new Line();
        line.setId(id);
        line.setName(name);
        line.setLineType(lineType);

        if(route != null) {
            route.addLine(line);
        }

        return line;
    }

}