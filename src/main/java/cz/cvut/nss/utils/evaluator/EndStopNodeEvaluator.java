package cz.cvut.nss.utils.evaluator;

import com.google.common.collect.Sets;
import cz.cvut.nss.entities.neo4j.StopNode;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Evaluator for neo4j traversal (finding paths to end station)
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public final class EndStopNodeEvaluator implements Evaluator {

    private final Long endStationId;

    private final EndStopNodeEvaluatorType searchingType;

    Map<Long, Long> foundedPaths = new HashMap<>();

    Map<Long, Set<Long>> foundedPathsDetails = new HashMap<>();

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
        if(foundedPaths.containsKey(startNodeStopId) && foundedPaths.get(startNodeStopId) < currentNodeTimeProperty) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //uz jsem nasel 3 vysledky
        int i = 0;
        if(foundedPathsDetails.size() >= 3) {
            for(Long key : foundedPathsDetails.keySet()) {
                if(foundedPaths.get(key) < currentNodeTimeProperty) {
                    i++;
                }

                if(i >= 3) {
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            }
        }

        //nasel jsem
        if(currentNodeStationId == endStationId) {
            Set<Long> tmpRides = new HashSet<>();
            for(Node n : path.nodes()) {
                tmpRides.add((long) n.getProperty(StopNode.RIDE_PROPERTY));
            }

            boolean saveMe = true;
            for (Map.Entry<Long, Set<Long>> entry : foundedPathsDetails.entrySet()) {
                Long key = entry.getKey();
                Set<Long> value = entry.getValue();

                if(!Sets.intersection(tmpRides, value).isEmpty()) {
                    if(foundedPaths.get(key) < currentNodeTimeProperty) {
                        saveMe = false;
                        break;
                    } else {
                        foundedPathsDetails.remove(key);
                        break;
                    }
                }
            }

            if(saveMe) {
                foundedPathsDetails.put(startNodeStopId, tmpRides);
            }

            foundedPaths.put(startNodeStopId, currentNodeTimeProperty);
            return Evaluation.INCLUDE_AND_PRUNE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
