package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;

/**
 * OperationInterval Neo4j service.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface OperationIntervalNeo4jService {

    /**
     * ulozi operationIntervalNod
     * @param operationIntervalNode operationIntervalNode
     * @return ulozeny OperationIntervalNode
     */
    OperationIntervalNode save(OperationIntervalNode operationIntervalNode);

    /**
     * @return vsechny operationIntervalNody
     */
    Iterable<OperationIntervalNode> findAll();

    /**
     * najde operationIntervalNode dle id uzlu
     * @param id id uzlu
     * @return operationIntervalNode dle id uzlu
     */
    OperationIntervalNode findById(long id);

    /**
     * najde operationIntervalNode dle operationIntervalId
     * @param operationIntervalId operationIntervalId
     * @return operationIntervalNode dle jeho operationIntervalId
     */
    OperationIntervalNode findByOperationIntervalId(long operationIntervalId);

    /**
     * provede update operationIntervalNode
     * @param operationIntervalNode operationIntervalNode k updatu
     * @return updatovany operationIntervalNode
     */
    OperationIntervalNode update(OperationIntervalNode operationIntervalNode);

    /**
     * smaze vsechny operationIntervalNody
     */
    void deleteAll();

    /**
     * smaze operationIntervalNode dle jeho operationIntervalId
     * @param operationIntervalId operationIntervalId
     */
    void deleteByOperationIntervalId(long operationIntervalId);

}
