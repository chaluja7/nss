package cz.cvut.nss.services;

import cz.cvut.nss.entities.Station;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Station service test.
 *
 * Created by jakubchalupa on 20.11.14.
 */
public class StationServiceTest extends AbstractServiceTest {

    @Autowired
    private StationService stationService;

    @Test
    public void testCreateAndGet() {
        Station station = prepareStation();

        Station retrieved = stationService.getStation(station.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(station.getName(), retrieved.getName());
        Assert.assertEquals(station.getTitle(), retrieved.getTitle());
    }

    @Test
    public void testUpdate() {
        Station station = prepareStation();

        Station retrieved = stationService.getStation(station.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setName("newName");
        retrieved.setTitle("newTitle");
        stationService.updateStation(retrieved);

        Station updated = stationService.getStation(retrieved.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getName(), updated.getName());
        Assert.assertEquals(retrieved.getTitle(), updated.getTitle());
    }

    @Test
    public void testDelete() {
        Station station = prepareStation();

        Station retrieved = stationService.getStation(station.getId());
        Assert.assertNotNull(retrieved);

        stationService.deleteStation(retrieved.getId());
        Assert.assertNull(stationService.getStation(station.getId()));
    }

    private Station prepareStation() {
        Station station = new Station();
        station.setName("testStation");
        station.setTitle("testStationTitle");
        station.setLongtitude(20.0);
        station.setLatitude(30.0);

        stationService.createStation(station);
        return station;
    }

    /*@Test
    public void superTest() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);

        try {
            date = dateFormat.parse("12.12.2014 05:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        List<SearchResultWrapper> foundedStops = searchDao.findRides(1, 5, date, 2);

    }*/

}
