package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluator for neo4j traversal (finding paths to end station)
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public final class EndStopNodeEvaluator implements Evaluator {

    private final Long endStationId;

    private final Long maxDepartureInMillis;

    private final int maxTransfers;

    public EndStopNodeEvaluator(Long endStationId, Long maxDepartureInMillis, int maxTransfers) {
        this.endStationId = endStationId;
        this.maxDepartureInMillis = maxDepartureInMillis;
        this.maxTransfers = maxTransfers;
    }

    @Override
    public Evaluation evaluate(Path path) {

        Node currentNode = path.endNode();

        int totalNumberOfRelationships = 0;
        int numberOfAwaitingRelationships = 0;
        RelTypes prevRelationShipType = null;

        for(Relationship relationship : path.relationships()) {
            totalNumberOfRelationships++;

            if(relationship.isType(RelTypes.NEXT_AWAITING_STOP)) {
                numberOfAwaitingRelationships++;
            }

            //pokud je tam jiz vice prestupu, nez je povoleno nebo pokud jsou za sebou 2 hrany next_awaiting_stop
            if(numberOfAwaitingRelationships > maxTransfers || (prevRelationShipType != null && prevRelationShipType.equals(RelTypes.NEXT_AWAITING_STOP) && relationship.isType(RelTypes.NEXT_AWAITING_STOP))) {
                return Evaluation.EXCLUDE_AND_PRUNE;
            }

            if(relationship.isType(RelTypes.NEXT_AWAITING_STOP)) {
                prevRelationShipType = RelTypes.NEXT_AWAITING_STOP;
            } else {
                prevRelationShipType = RelTypes.NEXT_STOP;
            }
        }

        //zjistim, jestli se nahodou nevracim zpatky - tedy stalo se napr STOP(Station1) - STOP(Station2) - STOP(Station1)
        int totalNumberOfNodes = 0;
        List<Node> lastNodesList = new ArrayList();
        for(Node node : path.reverseNodes()) {
            lastNodesList.add(node);

            if(++totalNumberOfNodes >= 3) {
                break;
            }
        }

        if(totalNumberOfNodes >= 3) {
            Node lastNode = lastNodesList.get(0);
            Node prevPrevNode = lastNodesList.get(2);

            if(lastNode.getProperty(StopNode.STATION_PROPERTY).equals(prevPrevNode.getProperty(StopNode.STATION_PROPERTY))) {
                return Evaluation.EXCLUDE_AND_PRUNE;
            }
        }

        //pokud byla prvni hrana next_awaiting_stop
        if(totalNumberOfRelationships == 1 && prevRelationShipType.equals(RelTypes.NEXT_AWAITING_STOP)) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //nasel jsem
        if(currentNode.getProperty(StopNode.STATION_PROPERTY).equals(endStationId)) {
            return Evaluation.INCLUDE_AND_PRUNE;
        }

        //mimo casovy rozsah
        if(prevRelationShipType != null && prevRelationShipType.equals(RelTypes.NEXT_AWAITING_STOP)
                && currentNode.hasProperty(StopNode.DEPARTURE_PROPERTY) && ((Long) currentNode.getProperty(StopNode.DEPARTURE_PROPERTY) > maxDepartureInMillis)) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
