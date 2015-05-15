package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.services.OperationIntervalService;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.services.StationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
    private RideNeo4jService rideNeo4jService;

    @Autowired
    private OperationIntervalNeo4jService operationIntervalNeo4jService;

    @Autowired
    private StationService stationService;

    @Autowired
    private RideService rideService;

    @Autowired
    private OperationIntervalService operationIntervalService;

    @Test
    public void test() {

    }


}




