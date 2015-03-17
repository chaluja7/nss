package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.entities.neo4j.StationNode;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.StopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 15.03.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
//@TransactionConfiguration(transactionManager = "neo4jTransactionManager")
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
    private StopService stopService;

    @Test
    public void testIt() {
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

        List<Stop> stopList = stopService.getAll();
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
            stopNeo4jService.create(stopNode);
        }

        Iterable<StationNode> stationNodeIterable = stationNeo4jService.findAll();
        List<StationNode> stationNodeList = new ArrayList<>();
        Iterator<StationNode> stationNodeIterator = stationNodeIterable.iterator();
        while(stationNodeIterator.hasNext()) {
            stationNodeList.add(stationNodeIterator.next());
        }

        Iterable<StopNode> stopNodeIterable = stopNeo4jService.findAll();
        List<StopNode> stopNodeList = new ArrayList<>();
        Iterator<StopNode> stopNodeIterator = stopNodeIterable.iterator();
        while(stopNodeIterator.hasNext()) {
            stopNodeList.add(stopNodeIterator.next());
        }


        int i = 0;
    }


}
