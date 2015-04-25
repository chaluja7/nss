package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Evaluator for neo4j traversal (finding paths to end station)
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public final class EndStopNodeEvaluator implements Evaluator {

    private final Long endStationId;

    private final EndStopNodeEvaluatorType searchingType;

    List<Long> foundedPathsArrival = new ArrayList<>();

    Map<Long, Long> foundedPaths = new HashMap<>();

    public EndStopNodeEvaluator(Long endStationId, EndStopNodeEvaluatorType searchingType) {
        this.endStationId = endStationId;
        this.searchingType = searchingType;
    }

    @Override
    public Evaluation evaluate(Path path) {
        //todo pres pulnoc

        Node startNode = path.startNode();
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);

        Node currentNode = path.endNode();
        long currentNodeStationId = (long) currentNode.getProperty(StopNode.STATION_PROPERTY);

        //rozhodujici je arrival time pokud existuje, jinak departure time
        Long currentNodeArrival = null;
        Long currentNodeDeparture = null;
        if (currentNode.hasProperty(StopNode.ARRIVAL_PROPERTY)) {
            currentNodeArrival = (Long) currentNode.getProperty(StopNode.ARRIVAL_PROPERTY);
        }
        if (currentNode.hasProperty(StopNode.DEPARTURE_PROPERTY)) {
            currentNodeDeparture = (Long) currentNode.getProperty(StopNode.DEPARTURE_PROPERTY);
        }

        if(currentNodeArrival == null && currentNodeDeparture == null) {
            throw new RuntimeException();
        }

        Long currentNodeTimeProperty = currentNodeArrival != null ? currentNodeArrival : currentNodeDeparture;

        //POKUD jsem jiz na teto ceste (od start node) nasel cil v lepsim case
        if(foundedPaths.containsKey(startNodeStopId) && foundedPaths.get(startNodeStopId) <= currentNodeTimeProperty) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //jestli uz jsem nenasel 3 lepsi vysledky
//        int i = 0;
//        for(Long alreadyFoundedArrival : foundedPathsArrival) {
//            if(alreadyFoundedArrival <= currentNodeTimeProperty) {
//                i++;
//            }
//
//            if(i >= 3) {
//                return Evaluation.EXCLUDE_AND_PRUNE;
//            }
//        }

        //uz jsem nasel 3 vysledky
        int i = 0;
        if(foundedPaths.size() >= 3) {
            for(long arrival : foundedPaths.values()) {
                if(arrival <= currentNodeTimeProperty) {
                    i++;
                }

                if(i >= 3) {
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            }
        }

        //nasel jsem
        if(currentNodeStationId == endStationId) {
            foundedPaths.put(startNodeStopId, currentNodeTimeProperty);
            return Evaluation.INCLUDE_AND_PRUNE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
