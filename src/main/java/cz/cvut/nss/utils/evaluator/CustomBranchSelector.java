package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.traversal.BranchSelector;
import org.neo4j.graphdb.traversal.TraversalBranch;
import org.neo4j.graphdb.traversal.TraversalContext;

/**
 * @author jakubchalupa
 * @since 23.04.15
 */
public class CustomBranchSelector implements BranchSelector {

    private final CustomPriorityQueue queue = new CustomPriorityQueue();
    private TraversalBranch current;
    private final PathExpander expander;

    public CustomBranchSelector(TraversalBranch startSource, PathExpander expander)
    {
        this.current = startSource;
        this.expander = expander;
    }

    @Override
    public TraversalBranch next(TraversalContext metadata) {
        TraversalBranch result = null;

        while (result == null) {
            TraversalBranch next = current.next(expander, metadata);
            if(next != null) {
                //dle zpusobu, kterym jsem se sem dostal to prislusne zaradim do fronty
                if(next.lastRelationship() == null) {
                    //prvni node Path
                    queue.addPath(next);
                } else if(next.lastRelationship().isType(RelTypes.NEXT_STOP)) {
                    //node po NEXT_STOP relaci
                    queue.addNextStop(next);
                } else {
                    //node po NEXT_AWAITING_STOP relaci
                    queue.addNextAwaitingStop(next);
                }

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
