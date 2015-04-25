package cz.cvut.nss.services;

import cz.cvut.nss.entities.OperationInterval;

import java.util.List;

/**
 * Common interface for all LineService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface OperationIntervalService {

    public OperationInterval getOperationInterval(long id);

    public OperationInterval updateOperationInterval(OperationInterval operationInterval);

    public void createOperationInterval(OperationInterval operationInterval);

    public void deleteOperationInterval(long id);

    public List<OperationInterval> getAll();

}
