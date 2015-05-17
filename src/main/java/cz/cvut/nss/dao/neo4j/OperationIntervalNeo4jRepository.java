package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * OperationInterval Neo4j repository.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface OperationIntervalNeo4jRepository extends GraphRepository<OperationIntervalNode> {

    /**
     * najde OperationIntervalNode dle jeho operationIntervalId
     * @param operationIntervalId operationIntervalId
     * @return node dle jeho id
     */
    @Query("match (n:OperationIntervalNode {operationIntervalId: {0}}) return n")
    OperationIntervalNode findByOperationIntervalId(Long operationIntervalId);

}
