package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.RideNode;
import cz.cvut.nss.entities.neo4j.StopNode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Stop Neo4j service tests.
 *
 * @author jakubchalupa
 * @since 15.03.15
 */
public class StopNeo4JServiceIT extends AbstractServiceNeo4jIT {

    @Autowired
    private StopNeo4jService stopNeo4jService;

    @Autowired
    private RideNeo4jService rideNeo4jService;

    @Test
    public void testCreateAndGet() {
        StopNode stopNode = prepareStop();
        StopNode savedStop = stopNeo4jService.save(stopNode);

        StopNode retrieved = stopNeo4jService.findById(savedStop.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getStationId());
        Assert.assertNotNull(retrieved.getArrivalInMillis());
        Assert.assertNotNull(retrieved.getDepartureInMillis());
        Assert.assertNotNull(retrieved.getRideNode());
    }

    @Test
    public void testUpdate() {
        StopNode stopNode = prepareStop();
        StopNode savedStop = stopNeo4jService.save(stopNode);

        StopNode retrieved = stopNeo4jService.findById(savedStop.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setStationId(-99l);
        stopNeo4jService.save(retrieved);

        StopNode updated = stopNeo4jService.findById(savedStop.getId());
        Assert.assertNotNull(updated);
        Assert.assertTrue(-99l == updated.getStationId());
    }


    private StopNode prepareStop() {
        RideNode rideNode = new RideNode();
        rideNode.setRideId(-1l);
        RideNode savedRideNode = rideNeo4jService.save(rideNode);

        StopNode stopNode = new StopNode();
        stopNode.setStopId(Long.MAX_VALUE);
        stopNode.setStationId(10l);
        stopNode.setDepartureInMillis(1l);
        stopNode.setArrivalInMillis(5l);
        stopNode.setRideId(-1l);
        stopNode.setRideNode(savedRideNode);

        return stopNode;
    }


}




