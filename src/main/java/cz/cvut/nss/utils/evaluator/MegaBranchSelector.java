package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import cz.cvut.nss.utils.DateTimeUtils;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.BranchSelector;
import org.neo4j.graphdb.traversal.TraversalBranch;
import org.neo4j.graphdb.traversal.TraversalContext;

/**
 * @author jakubchalupa
 * @since 23.04.15
 */
public class MegaBranchSelector implements BranchSelector {

    private final MegaPriorityQueue queue = new MegaPriorityQueue();

    private TraversalBranch current;

    private final PathExpander expander;

    public MegaBranchSelector(TraversalBranch startSource, PathExpander expander) {
        this.current = startSource;
        this.expander = expander;
    }

    @Override
    public TraversalBranch next(TraversalContext metadata) {
        TraversalBranch result = null;

        while (result == null) {
            TraversalBranch next = current.next(expander, metadata);
            if(next != null) {
                Node startNode = next.startNode();
                Node endNode = next.endNode();

                long departureTime = (long) startNode.getProperty(StopNode.DEPARTURE_PROPERTY);
                long currentTime;
                if(endNode.hasProperty(StopNode.ARRIVAL_PROPERTY)) {
                    currentTime = (long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY);
                } else {
                    currentTime = (long) endNode.getProperty(StopNode.DEPARTURE_PROPERTY);
                }

                long travelTime;
                if(departureTime <= currentTime) {
                    travelTime = currentTime - departureTime;
                } else {
                    travelTime = DateTimeUtils.MILLIS_IN_DAY - departureTime + currentTime;
                }


                int numOfTransfers = 0;
                RelTypes prevRelationShipType = null;
                for(Relationship relationship : next.reverseRelationships()) {
                    boolean relationshipIsTypeNextAwaitingStop = relationship.isType(RelTypes.NEXT_AWAITING_STOP);
                    //sel jsem (N)-[NEXT_AWAITING_STOP]-(m)-[NEXT_STOP]-(o)
                    if(prevRelationShipType != null && relationshipIsTypeNextAwaitingStop && prevRelationShipType.equals(RelTypes.NEXT_STOP)) {
                        numOfTransfers++;
                    }

                    if(relationshipIsTypeNextAwaitingStop) {
                        prevRelationShipType = RelTypes.NEXT_AWAITING_STOP;
                    } else {
                        prevRelationShipType = RelTypes.NEXT_STOP;
                    }
                }

                travelTime = travelTime + (numOfTransfers * DateTimeUtils.TRANSFER_PENALTY_MILLIS);


                queue.addPath(next, currentTime / 1000, travelTime / 1000);
                result = next;
            } else {
                current = queue.poll();
                if(current == null) {
                    return null;
                }
            }

        }

        return result;
    }

}
