package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
    @Qualifier("neo4jSearchService")
    private SearchService searchService;

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

}




