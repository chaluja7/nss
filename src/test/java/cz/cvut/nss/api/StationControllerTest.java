package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.StationResource;
import cz.cvut.nss.entities.Region;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
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
public class StationControllerTest {

    private StationController stationController;

    private StationService stationServiceMock;

    @Before
    public void setUp() throws Exception {
        stationController = new StationController();

        stationServiceMock = Mockito.mock(StationService.class);
        stationController.stationService = stationServiceMock;
    }

    @Test
    public void testGetStations() throws Exception {
        Mockito.when(stationServiceMock.getAll()).thenReturn(getStations());

        List<StationResource> stations = stationController.getStations();
        Assert.assertNotNull(stations);
        Assert.assertEquals(2, stations.size());

        for(StationResource station : stations) {
            Assert.assertNotNull(station);
            Assert.assertNotNull(station.getId());
            Assert.assertNotNull(station.getName());
            Assert.assertNotNull(station.getRegion());
        }
    }

    @Test
    public void testGetStationsForDataTable() throws Exception {
        EntitiesAndCountResult<Station> result = new EntitiesAndCountResult<>();
        result.setEntities(getStations());
        final int currentSize = result.getEntities().size();
        result.setCount(currentSize);

        final int countAll = 4;

        Mockito.when(stationServiceMock.getAllForDatatables(Mockito.any(CommonRequest.class))).thenReturn(result);
        Mockito.when(stationServiceMock.getCountAll()).thenReturn(countAll);

        DataTableResource<StationResource> stationsForDataTable = stationController.getStationsForDataTable(new CommonRequest());
        Assert.assertNotNull(stationsForDataTable);
        Assert.assertNotNull(stationsForDataTable.getAaData());
        Assert.assertEquals(currentSize, stationsForDataTable.getAaData().size());

        Assert.assertEquals(currentSize, stationsForDataTable.getRecordsFiltered());
        Assert.assertEquals(countAll, stationsForDataTable.getRecordsTotal());
    }

    @Test
    public void testGetStationsTitleByPattern() throws Exception {
        List<Station> stations = getStations();

        Mockito.when(stationServiceMock.getAllByNamePattern(Mockito.anyString())).thenReturn(stations);

        List<String> stationNames = stationController.getStationsTitleByPattern("pattern");
        Assert.assertNotNull(stationNames);
        Assert.assertEquals(2, stationNames.size());

        for(Station station : stations) {
            Assert.assertTrue(stationNames.contains(station.getName()));
        }
    }

    public static List<Station> getStations() {
        Region region = getRegion(1L, "region1");
        List<Station> list = new ArrayList<>();
        list.add(getStation(1L, "station1", region));
        list.add(getStation(2L, "station2", region));

        return list;
    }

    public static Station getStation(Long id, String name, Region region) {
        Station station = new Station();
        station.setId(id);
        station.setName(name);
        station.setRegion(region);
        //dalsi property by byly zde

        return station;
    }

    public static Region getRegion(Long id, String name) {
        Region region = new Region();
        region.setId(id);
        region.setName(name);

        return region;
    }

}