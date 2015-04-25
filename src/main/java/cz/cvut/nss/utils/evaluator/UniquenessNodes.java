package cz.cvut.nss.utils.evaluator;

import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.traversal.BranchOrderingPolicy;
import org.neo4j.graphdb.traversal.BranchSelector;
import org.neo4j.graphdb.traversal.TraversalBranch;

/**
 * @author jakubchalupa
 * @since 19.04.15
 */
public class UniquenessNodes implements BranchOrderingPolicy {

    @Override
    public BranchSelector create(TraversalBranch propertyContainers, PathExpander pathExpander) {
        return null;
    }

}
