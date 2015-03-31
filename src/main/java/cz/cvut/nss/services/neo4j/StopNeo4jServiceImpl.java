package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.dao.neo4j.StopNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.services.neo4j.impl.StopNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Stop Neo4j service implementation.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
@Service
@Transactional("neo4jTransactionManager")
public class StopNeo4jServiceImpl implements StopNeo4jService {

    @Autowired
    protected StopNeo4jRepository stopNeo4jRepository;

    @Override
    public StopNode save(StopNode stop) {
        return stopNeo4jRepository.save(stop);
    }

    @Override
    public Iterable<StopNode> findAll() {
        return stopNeo4jRepository.findAll();
    }

    @Override
    public StopNode findById(long id) {
        return stopNeo4jRepository.findOne(id);
    }

    @Override
    public void deleteAll() {
        stopNeo4jRepository.deleteAll();
    }

    @Override
    public Set<StopNode> findNotStartingStopNodesByStationOrderByArrival(Long stationId) {
        return stopNeo4jRepository.findNotStartingStopNodesByStationOrderByArrival(stationId);
    }

    @Override
    public Set<StopNode> findByStationAndDepartureRange(Long stationId, Long departureInMillisMin, Long departureInMillisMax) {
        return stopNeo4jRepository.findByStationAndDepartureRange(stationId, departureInMillisMin, departureInMillisMax);
    }

}
