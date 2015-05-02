package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.traversal.BranchSelector;
import org.neo4j.graphdb.traversal.TraversalBranch;
import org.neo4j.graphdb.traversal.TraversalContext;

import java.util.LinkedList;

/**
 * @author jakubchalupa
 * @since 23.04.15
 */
public class CustomBranchSelector implements BranchSelector {

    private final CustomPriorityQueue queue2 = new CustomPriorityQueue();

    private final LinkedList<TraversalBranch> queue = new LinkedList<>();
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
        while ( result == null )
        {

            TraversalBranch next = current.next(expander, metadata);
            if(next != null) {
                if(next.lastRelationship() == null) {
                    queue2.addPath(next);
                } else if(next.lastRelationship().isType(RelTypes.NEXT_STOP)) {
                    queue2.addNextStop(next);
                } else {
                    queue2.addNextAwaitingStop(next);
                }

                result = next;
            } else {
                current = queue2.poll();

                if(current == null) {
                    return null;
                }
            }

//            TraversalBranch next = current.next( expander, metadata );
//            if ( next != null )
//            {
//
//                if(next.lastRelationship() != null && next.lastRelationship().isType(RelTypes.NEXT_STOP)) {
//                    queue.addFirst(next);
//                } else {
//                    queue.add(next);
//                }
//
//                result = next;
//            }
//            else
//            {
//                current = queue.poll();
//                if ( current == null )
//                {
//                    return null;
//                }
//            }
        }
        return result;
    }

}
