package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.RideResource;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.enums.LineType;
import cz.cvut.nss.services.RideService;
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
public class RideControllerTest {

    private RideController rideController;

    private RideService rideServiceMock;

    @Before
    public void setUp() throws Exception {
        rideController = new RideController();

        rideServiceMock = Mockito.mock(RideService.class);
        rideController.rideService = rideServiceMock;
    }

    @Test
    public void testGetRides() throws Exception {
        Mockito.when(rideServiceMock.getAll()).thenReturn(getRides());

        List<RideResource> rides = rideController.getRides();
        Assert.assertNotNull(rides);
        Assert.assertEquals(2, rides.size());

        for(RideResource ride : rides) {
            Assert.assertNotNull(ride);
            Assert.assertNotNull(ride.getId());
            Assert.assertNotNull(ride.getOperationInterval());
        }
    }

    @Test
    public void testGetRidesForDataTable() throws Exception {
        DataTableResource<RideResource> ridesForDataTable = rideController.getRidesForDataTable(null);
        Assert.assertNotNull(ridesForDataTable);
        Assert.assertNull(ridesForDataTable.getAaData());
        Assert.assertEquals(0, ridesForDataTable.getRecordsFiltered());
        Assert.assertEquals(0, ridesForDataTable.getRecordsTotal());

        Long rideId = 1L;
        Mockito.when(rideServiceMock.getByLineId(Mockito.eq(rideId))).thenReturn(getRides());

        ridesForDataTable = rideController.getRidesForDataTable(rideId);
        Assert.assertNotNull(ridesForDataTable);
        Assert.assertNotNull(ridesForDataTable.getAaData());
        Assert.assertEquals(2, ridesForDataTable.getAaData().size());

    }

    public static List<Ride> getRides() {
        List<Ride> list = new ArrayList<>();

        list.add(getRide(1L, OperationIntervalControllerTest.getOperationInterval(1L, new LocalDate(), new LocalDate(), true, false),
            LineControllerTest.getLine(1L, "line1", LineType.BUS, null)));
        list.add(getRide(2L, OperationIntervalControllerTest.getOperationInterval(2L, new LocalDate(), new LocalDate(), false, true),
            LineControllerTest.getLine(2L, "line2", LineType.BOAT, null)));

        return list;
    }

    public static Ride getRide(Long id, OperationInterval operationInterval, Line line) {
        Ride ride = new Ride();
        ride.setId(id);
        ride.setOperationInterval(operationInterval);

        if(line != null) {
            line.addRide(ride);
        }

        return ride;
    }

}