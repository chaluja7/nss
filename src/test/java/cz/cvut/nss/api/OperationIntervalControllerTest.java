package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.OperationIntervalResource;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.services.OperationIntervalService;
import org.joda.time.LocalDate;
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
public class OperationIntervalControllerTest {

    private OperationIntervalController operationIntervalController;

    private OperationIntervalService operationIntervalServiceMock;

    @Before
    public void setUp() throws Exception {
        operationIntervalController = new OperationIntervalController();

        operationIntervalServiceMock = Mockito.mock(OperationIntervalService.class);
        operationIntervalController.operationIntervalService = operationIntervalServiceMock;
    }

    /**
     * otestovani metody controleru. Servisa se mockuje.
     * @throws Exception
     */
    @Test
    public void testGetOperationIntervals() throws Exception {
        Mockito.when(operationIntervalServiceMock.getAll()).thenReturn(getOperationIntervals());

        List<OperationIntervalResource> operationIntervals = operationIntervalController.getOperationIntervals();
        Assert.assertNotNull(operationIntervals);
        Assert.assertEquals(2, operationIntervals.size());

        for(OperationIntervalResource operationInterval : operationIntervals) {
            Assert.assertNotNull(operationInterval);
            Assert.assertNotNull(operationInterval.getId());
            Assert.assertNotNull(operationInterval.getStartDate());
            Assert.assertNotNull(operationInterval.getEndDate());
        }
    }

    /**
     * otestovani metody controleru. Servisa se mockuje.
     * @throws Exception
     */
    @Test
    public void testGetOperationIntervalsForDataTable() throws Exception {
        Mockito.when(operationIntervalServiceMock.getAll()).thenReturn(getOperationIntervals());

        DataTableResource<OperationIntervalResource> operationIntervalsForDataTable = operationIntervalController.getOperationIntervalsForDataTable();
        Assert.assertNotNull(operationIntervalsForDataTable);
        Assert.assertNotNull(operationIntervalsForDataTable.getAaData());
        Assert.assertEquals(2, operationIntervalsForDataTable.getAaData().size());
    }

    public static List<OperationInterval> getOperationIntervals() {
        List<OperationInterval> list = new ArrayList<>();
        list.add(getOperationInterval(1L, new LocalDate(), new LocalDate(), true, false));
        list.add(getOperationInterval(2L, new LocalDate(), new LocalDate(), false, true));

        return list;
    }

    public static OperationInterval getOperationInterval(Long id, LocalDate startDate, LocalDate endDate, boolean monday, boolean sunday) {
        OperationInterval operationInterval = new OperationInterval();
        operationInterval.setId(id);
        operationInterval.setStartDate(startDate);
        operationInterval.setEndDate(endDate);
        operationInterval.setMonday(monday);
        operationInterval.setSunday(sunday);
        //schvalne zde nejsou vsechny dny
        operationInterval.setTuesday(false);
        operationInterval.setWednesday(false);
        operationInterval.setThursday(false);
        operationInterval.setFriday(false);
        operationInterval.setSaturday(false);

        return operationInterval;
    }

}