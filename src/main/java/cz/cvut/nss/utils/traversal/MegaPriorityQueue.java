package cz.cvut.nss.utils.traversal;

import org.neo4j.graphdb.traversal.TraversalBranch;

import java.util.PriorityQueue;

/**
 * Priority queue
 *
 * @author jakubchalupa
 * @since 01.05.15
 */
public class MegaPriorityQueue {

    private PriorityQueue<TraversalBranchWrapper> priorityQueue = new PriorityQueue<>(new TraversalBranchWrapperComparator());

    public void addPath(TraversalBranch path, long nodeTime, long travelTime) {
        priorityQueue.add(new TraversalBranchWrapper(path, nodeTime, travelTime));
    }

    public TraversalBranch poll() {
        if(priorityQueue.isEmpty()) {
            return null;
        }

        TraversalBranchWrapper wrapper = priorityQueue.poll();
        return wrapper.getTraversalBranch();
    }

}
