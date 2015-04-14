package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 15.03.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class StationNeo4jServiceTest {

    @Autowired
    private StopNeo4jService stopNeo4jService;

    @Autowired
    private StationService stationService;

    @Autowired
    private RideService rideService;

    @Autowired
    private RegionService regionService;

    @Autowired
    @Qualifier("neo4jSearchService")
    private SearchService searchService;

    @Autowired
    private GoogleStationService googleStationService;

    @Test
    @Ignore
    public void deleteAllStopNodesTest() {
        stopNeo4jService.deleteAll();
    }

    @Test
    @Ignore
    public void importAllStopNodesTest() {

        //do neo4j vrazim jednotlive RIDES
        List<Ride> rideList = rideService.getAll();
        for(Ride ride : rideList) {
            rideService.importRideToNeo4j(ride.getId());
        }

        //Nyni proiteruji vsechny stanice a propojim prislusne stopy na dane stanici cekacimi hranamy
        for(Station station : stationService.getAll()) {
            stopNeo4jService.connectStopNodesOnStationWithWaitingStopRelationship(station.getId());
        }
    }

    @Test
    public void testSearch() {


        List<SearchResultWrapper> pathsWithTransfers = searchService.findPathByDepartureDate(4, 7, new Date(1418061600000l), 5, 3, 5);

        int i = 0;

    }

    @Test
    public void importStations() throws IOException {
        googleStationService.deleteAll();
        importParentStations();
        importChildStations();
    }

    public void importParentStations() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/BP/NSS/src/main/resources/20130625_20130625_stops.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

                String[] station = line.split(cvsSplitBy);
                if(station.length == 5) {
                    String stationId = station[0];
                    String stationName = station[1].replaceAll("\"", "");
                    double latitude = Double.parseDouble(station[2]);
                    double longitude = Double.parseDouble(station[3]);

                    String parentStationId = null;
                    Station stationByName = stationService.getStationByName(stationName);
                    if(stationByName != null) {
                        parentStationId = stationByName.getTitle();
                    } else {
                        Station stationJpa = new Station();
                        stationJpa.setTitle(stationId);
                        stationJpa.setName(stationName);
                        stationJpa.setLongtitude(longitude);
                        stationJpa.setLatitude(latitude);
                        stationJpa.setRegion(regionService.getRegion(1));
                        stationService.createStation(stationJpa);
                    }

                    googleStationService.createGoogleStation(stationId, stationName, latitude, longitude, parentStationId);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importChildStations() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/BP/NSS/src/main/resources/20130625_20130625_stops.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

                String[] station = line.split(cvsSplitBy);
                if(station.length == 6) {
                    String stationId = station[0];
                    String stationName = station[1].replaceAll("\"", "");
                    double latitude = Double.parseDouble(station[2]);
                    double longitude = Double.parseDouble(station[3]);

                    Station stationByName = stationService.getStationByName(stationName);
                    if(stationByName == null) {
                        throw new RuntimeException();
                    }

                    googleStationService.createGoogleStation(stationId, stationName, latitude, longitude, stationByName.getTitle());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}




