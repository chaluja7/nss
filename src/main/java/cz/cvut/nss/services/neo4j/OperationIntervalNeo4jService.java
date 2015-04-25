package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;

/**
 * Stop Neo4j service.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface OperationIntervalNeo4jService {

    OperationIntervalNode save(OperationIntervalNode operationIntervalNode);

    Iterable<OperationIntervalNode> findAll();

    OperationIntervalNode findById(long id);

    OperationIntervalNode findByOperationIntervalId(long operationIntervalId);

    void deleteAll();

}
