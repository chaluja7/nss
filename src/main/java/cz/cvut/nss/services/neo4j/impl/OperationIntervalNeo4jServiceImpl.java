package cz.cvut.nss.services.neo4j.impl;

import cz.cvut.nss.dao.neo4j.OperationIntervalNeo4jRepository;
import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import cz.cvut.nss.services.neo4j.OperationIntervalNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OperationInterval Neo4j service implementation.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
@Service
@Transactional("neo4jTransactionManager")
public class OperationIntervalNeo4jServiceImpl implements OperationIntervalNeo4jService {

    @Autowired
    protected OperationIntervalNeo4jRepository operationIntervalNeo4jRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public OperationIntervalNode save(OperationIntervalNode operationIntervalNode) {
        return operationIntervalNeo4jRepository.save(operationIntervalNode);
    }

    @Override
    public Iterable<OperationIntervalNode> findAll() {
        return operationIntervalNeo4jRepository.findAll();
    }

    @Override
    public OperationIntervalNode findById(long id) {
        return operationIntervalNeo4jRepository.findOne(id);
    }

    @Override
    public OperationIntervalNode findByOperationIntervalId(long operationIntervalId) {
        return operationIntervalNeo4jRepository.findByOperationIntervalId(operationIntervalId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public OperationIntervalNode update(OperationIntervalNode operationIntervalNode) {
        OperationIntervalNode byOperationIntervalId = operationIntervalNeo4jRepository.findByOperationIntervalId(operationIntervalNode.getOperationIntervalId());
        operationIntervalNode.setId(byOperationIntervalId.getId());

        return operationIntervalNeo4jRepository.save(operationIntervalNode);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteAll() {
        operationIntervalNeo4jRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteByOperationIntervalId(long operationIntervalId) {
        OperationIntervalNode node = findByOperationIntervalId(operationIntervalId);
        if(node != null) {
            operationIntervalNeo4jRepository.delete(node);
        }
    }

}
