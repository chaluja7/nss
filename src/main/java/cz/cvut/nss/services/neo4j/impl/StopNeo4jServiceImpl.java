package cz.cvut.nss.services.neo4j.impl;

import cz.cvut.nss.dao.neo4j.StopNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.services.neo4j.StopNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public StopNode findByStopId(long stopId) {
        return stopNeo4jRepository.findByStopId(stopId);
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

    @Override
    public void connectStopNodesOnStationWithWaitingStopRelationship(long stationId) {
        //Vyberu vsechny stopy na dane stanici serazene dle data odjezdu, pokud neni null, jinak dle data prijezdu
        List<StopNode> orderedStopNodes = findStopNodesByStationIdOrderByActionTime(stationId);

        int i = 0;
        StopNode firstStopNode = null;
        StopNode stopNodeFrom = null;
        for (StopNode stopNodeTo : orderedStopNodes) {
            if(i == 0) {
                firstStopNode = stopNodeTo;
            }

            if(stopNodeFrom != null) {
                stopNodeFrom.hasNextAwaitingStopNodeRelationShip(stopNodeTo, 0l);
                save(stopNodeFrom);
            }

            //propojeni v kruhu (posledni s prvnim)
            if(i != 0 && i == orderedStopNodes.size() - 1) {
                stopNodeTo.hasNextAwaitingStopNodeRelationShip(firstStopNode, 0l);
                save(stopNodeTo);
            }

            stopNodeFrom = stopNodeTo;
            i++;
        }
    }

    @Override
    public void deleteByStopId(long stopId) {
        StopNode stopNode = stopNeo4jRepository.findByStopId(stopId);
        if(stopNode != null) {
            stopNeo4jRepository.delete(stopNode);
        }
    }

    @Override
    public void deleteAllByRideId(long rideId) {
        for(StopNode stopNode : stopNeo4jRepository.findByRideId(rideId)) {
            stopNeo4jRepository.delete(stopNode);
        }
    }

    @Override
    public List<StopNode> findStopNodesByStationIdOrderByActionTime(Long stationId) {
        return stopNeo4jRepository.findStopNodesByStationIdOrderByActionTime(stationId);
    }

}
