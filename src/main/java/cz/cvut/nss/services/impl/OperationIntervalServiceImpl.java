package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.OperationIntervalDao;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.services.OperationIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 17.04.15
 */
@Service
public class OperationIntervalServiceImpl implements OperationIntervalService {

    @Autowired
    protected OperationIntervalDao operationIntervalDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public OperationInterval getOperationInterval(long id) {
        return operationIntervalDao.find(id);
    }

    @Override
    @Transactional
    public OperationInterval updateOperationInterval(OperationInterval operationInterval) {
        return operationIntervalDao.update(operationInterval);
    }

    @Override
    @Transactional
    public void createOperationInterval(OperationInterval operationInterval) {
        operationIntervalDao.create(operationInterval);
    }

    @Override
    @Transactional
    public void deleteOperationInterval(long id) {
        operationIntervalDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<OperationInterval> getAll() {
        return operationIntervalDao.findAll();
    }

}
