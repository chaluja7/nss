package cz.cvut.nss.utils.traversal;

import org.neo4j.graphdb.traversal.TraversalBranch;

/**
 * @author jakubchalupa
 * @since 13.05.15
 */
public class TraversalBranchWrapper {

    private TraversalBranch traversalBranch;

    private long nodeTime;

    private long travelTime;

    public TraversalBranchWrapper(TraversalBranch traversalBranch, long nodeTime, long travelTime) {
        this.traversalBranch = traversalBranch;
        this.nodeTime = nodeTime;
        this.travelTime = travelTime;
    }

    public TraversalBranch getTraversalBranch() {
        return traversalBranch;
    }

    public void setTraversalBranch(TraversalBranch traversalBranch) {
        this.traversalBranch = traversalBranch;
    }

    public long getNodeTime() {
        return nodeTime;
    }

    public void setNodeTime(long nodeTime) {
        this.nodeTime = nodeTime;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
}
