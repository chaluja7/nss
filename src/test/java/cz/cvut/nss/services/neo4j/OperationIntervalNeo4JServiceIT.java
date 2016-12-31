package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OperationInterval Neo4j service tests.
 *
 * @author jakubchalupa
 * @since 15.05.15
 */
public class OperationIntervalNeo4JServiceIT extends AbstractServiceNeo4jIT {

    @Autowired
    private OperationIntervalNeo4jService operationIntervalNeo4jService;

    @Test
    public void testCreateAndGet() {
        OperationIntervalNode operationIntervalNode = prepareOperationInterval();
        OperationIntervalNode saved = operationIntervalNeo4jService.save(operationIntervalNode);

        OperationIntervalNode retrieved = operationIntervalNeo4jService.findById(saved.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getFromDateInMillis());
        Assert.assertNotNull(retrieved.getToDateInMillis());
    }

    @Test
    public void testUpdate() {
        OperationIntervalNode operationIntervalNode = prepareOperationInterval();
        OperationIntervalNode saved = operationIntervalNeo4jService.save(operationIntervalNode);

        OperationIntervalNode retrieved = operationIntervalNeo4jService.findById(saved.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setToDateInMillis(99l);
        operationIntervalNeo4jService.save(retrieved);

        retrieved = operationIntervalNeo4jService.findById(saved.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getToDateInMillis());
        Assert.assertTrue(99l == retrieved.getToDateInMillis());
    }

    private OperationIntervalNode prepareOperationInterval() {
        OperationIntervalNode operationIntervalNode = new OperationIntervalNode();
        operationIntervalNode.setOperationIntervalId(-1l);
        operationIntervalNode.setFromDateInMillis(-1l);
        operationIntervalNode.setToDateInMillis(-5l);
        operationIntervalNode.setMonday(true);
        operationIntervalNode.setTuesday(true);

        return operationIntervalNode;
    }

}
