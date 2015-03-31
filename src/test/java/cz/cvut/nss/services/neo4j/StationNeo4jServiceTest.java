package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.entities.neo4j.StationNode;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.neo4j.impl.StationNeo4jService;
import cz.cvut.nss.services.neo4j.impl.StopNeo4jService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    private StationNeo4jService stationNeo4jService;

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
    public void testIt() {
        //stop 1 departure: 1418011560000
        //stop 232 departure: 1418060160000

//        match (n:StopNode {stationId: 1})-[nextStop*]->(m:StopNode {stationId: 7}) where n.departureInMillis >= 1418011560000 and n.departureInMillis <= 1418060160000 return nextStop

        stationNeo4jService.deleteAll();
        stopNeo4jService.deleteAll();
    }

    @Test
    public void superTest() {
        List<Station> stationList = stationService.getAll();
        for(Station station : stationList) {
            StationNode stationNode = new StationNode();
            stationNode.setStationId(station.getId());
            stationNode.setTitle(station.getTitle());
            stationNode.setName(station.getName());

            stationNeo4jService.create(stationNode);
        }

        List<Ride> rideList = rideService.getAll();
        for(Ride ride : rideList) {
            List<Stop> stopList = ride.getStops();
            StopNode prevStopNode = null;
            for(Stop stop : stopList) {
                StopNode stopNode = new StopNode();
                stopNode.setStopId(stop.getId());
                stopNode.setStationId(stop.getStation().getId());
                stopNode.setRideId(stop.getRide().getId());
                if(stop.getArrival() != null) {
                    stopNode.setArrivalInMillis(stop.getArrival().toDateTime().getMillis());
                }
                if(stop.getDeparture() != null) {
                    stopNode.setDepartureInMillis(stop.getDeparture().toDateTime().getMillis());
                }
                stopNode.setStationNode(stationNeo4jService.findOneByLongProperty("stationId", stop.getStation().getId()));

                if(prevStopNode != null) {
                    stopNode.hasPrevStopNodeRelationShip(prevStopNode, stopNode.getArrivalInMillis() - prevStopNode.getDepartureInMillis());
                }

                prevStopNode = stopNeo4jService.save(stopNode);
            }
        }


        Iterable<StationNode> allStationNodesIterable = stationNeo4jService.findAll();
        Iterator<StationNode> allStationNodesIterator = allStationNodesIterable.iterator();
        while(allStationNodesIterator.hasNext()) {
            StationNode stationNode = allStationNodesIterator.next();

            Set<StopNode> notStartingStopNodesByStationOrderByArrival = stopNeo4jService.findNotStartingStopNodesByStationOrderByArrival(stationNode.getStationId());
            for (StopNode stopNodeFrom : notStartingStopNodesByStationOrderByArrival) {

                long maxDepartureInMillis = stopNodeFrom.getArrivalInMillis() + 86400000;
                Set<StopNode> possibleAwaitingTransferStopNodes = stopNeo4jService.findByStationAndDepartureRange(stopNodeFrom.getStationId(), stopNodeFrom.getArrivalInMillis(), maxDepartureInMillis);

                for (StopNode stopNodeTo : possibleAwaitingTransferStopNodes) {
                    stopNodeFrom.hasNextAwaitingStopNodeRelationShip(stopNodeTo, stopNodeTo.getDepartureInMillis() - stopNodeFrom.getArrivalInMillis());
                }

                stopNeo4jService.save(stopNodeFrom);
            }
        }

    }

    @Test
    public void testSearch() {


        List<SearchResultWrapper> pathsWithTransfers = searchService.findPathByDepartureDate(4, 7, new Date(1418061600000l), 5, 3, 5);

        int i = 0;

    }

}




