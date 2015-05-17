package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.OperationIntervalDao;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import cz.cvut.nss.services.OperationIntervalService;
import cz.cvut.nss.services.neo4j.OperationIntervalNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of OperationIntervalService.
 *
 * @author jakubchalupa
 * @since 17.04.15
 */
@Service
public class OperationIntervalServiceImpl implements OperationIntervalService {

    @Autowired
    protected OperationIntervalDao operationIntervalDao;

    @Autowired
    protected OperationIntervalNeo4jService operationIntervalNeo4jService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public OperationInterval getOperationInterval(long id) {
        return operationIntervalDao.find(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public OperationInterval updateOperationInterval(OperationInterval operationInterval) {
        return operationIntervalDao.update(operationInterval);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createOperationInterval(OperationInterval operationInterval) {
        operationIntervalDao.create(operationInterval);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteOperationInterval(long id) {
        operationIntervalDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<OperationInterval> getAll() {
        return operationIntervalDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void importOperationIntervalToNeo4j(OperationInterval operationInterval) {

        OperationIntervalNode node = new OperationIntervalNode();

        node.setOperationIntervalId(operationInterval.getId());
        node.setMonday(operationInterval.getMonday());
        node.setTuesday(operationInterval.getTuesday());
        node.setWednesday(operationInterval.getWednesday());
        node.setThursday(operationInterval.getThursday());
        node.setFriday(operationInterval.getFriday());
        node.setSaturday(operationInterval.getSaturday());
        node.setSunday(operationInterval.getSunday());
        node.setFromDateInMillis(operationInterval.getStartDate().toDate().getTime());
        node.setToDateInMillis(operationInterval.getEndDate().toDate().getTime());

        operationIntervalNeo4jService.save(node);
    }

}
