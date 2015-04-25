package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Stop Neo4j repository.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface OperationIntervalNeo4jRepository extends GraphRepository<OperationIntervalNode> {

    @Query("match (n:OperationIntervalNode {operationIntervalId: {0}}) return n")
    OperationIntervalNode findByOperationIntervalId(Long operationIntervalId);

}
