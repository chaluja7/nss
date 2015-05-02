package cz.cvut.nss.utils.evaluator;

import org.neo4j.graphdb.traversal.TraversalBranch;

import java.util.LinkedList;

/**
 * @author jakubchalupa
 * @since 01.05.15
 */
public class CustomPriorityQueue {

    private final LinkedList<TraversalBranch> paths = new LinkedList<>();

    private final LinkedList<TraversalBranch> nextStops = new LinkedList<>();

    private final LinkedList<TraversalBranch> nextAwaitingStops = new LinkedList<>();

    public void addPath(TraversalBranch path) {
        paths.add(path);
    }

    public void addNextStop(TraversalBranch nextStop) {
        nextStops.add(nextStop);
    }

    public void addNextAwaitingStop(TraversalBranch nextAwaitingStop) {
        nextAwaitingStops.add(nextAwaitingStop);
    }

    public TraversalBranch poll() {
        TraversalBranch traversalBranch;

        traversalBranch = nextStops.poll();
        if(traversalBranch != null) {
            return traversalBranch;
        }

        traversalBranch = nextAwaitingStops.poll();
        if(traversalBranch != null) {
            return traversalBranch;
        }

        traversalBranch = paths.poll();
        if(traversalBranch != null) {
            return traversalBranch;
        }

        return null;
    }

}
