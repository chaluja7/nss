package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import cz.cvut.nss.entities.neo4j.RideNode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jakubchalupa
 * @since 15.05.15
 */
public class RideNeo4jServiceTest extends AbstractServiceNeo4jTest {

    @Autowired
    private OperationIntervalNeo4jService operationIntervalNeo4jService;

    @Autowired
    private RideNeo4jService rideNeo4jService;

    @Test
    public void testCreateAndGet() {
        RideNode rideNode = prepareRideNode();
        RideNode saved = rideNeo4jService.save(rideNode);

        RideNode retrieved = rideNeo4jService.findById(saved.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getRideId());
    }

    @Test
    public void testUpdate() {
        RideNode rideNode = prepareRideNode();
        RideNode saved = rideNeo4jService.save(rideNode);

        RideNode retrieved = rideNeo4jService.findById(saved.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setRideId(-99l);
        rideNeo4jService.save(retrieved);

        retrieved = rideNeo4jService.findById(saved.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getRideId());
        Assert.assertTrue(-99l == retrieved.getRideId());
    }

    public RideNode prepareRideNode() {
        OperationIntervalNode operationIntervalNode = new OperationIntervalNode();
        operationIntervalNode.setOperationIntervalId(-1l);
        operationIntervalNode.setFromDateInMillis(-1l);
        operationIntervalNode.setToDateInMillis(-5l);
        operationIntervalNode.setMonday(true);
        operationIntervalNode.setTuesday(true);
        OperationIntervalNode savedOperationInterval = operationIntervalNeo4jService.save(operationIntervalNode);

        RideNode rideNode = new RideNode();
        rideNode.setRideId(-5l);
        rideNode.setOperationIntervalNode(savedOperationInterval);

        return rideNode;
    }

}
