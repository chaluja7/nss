package cz.cvut.nss.utils.evaluator;

import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.traversal.BranchOrderingPolicy;
import org.neo4j.graphdb.traversal.BranchSelector;
import org.neo4j.graphdb.traversal.TraversalBranch;

/**
 * @author jakubchalupa
 * @since 23.04.15
 */
public enum CustomBranchOrderingPolicies implements BranchOrderingPolicy {

    CUSTOM_ORDERING {
        public BranchSelector create(TraversalBranch startBranch, PathExpander expander) {
            return new CustomBranchSelector(startBranch, expander);
        }
    }

}
