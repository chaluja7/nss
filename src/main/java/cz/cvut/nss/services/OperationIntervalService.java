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

    OperationInterval getOperationInterval(long id);

    OperationInterval updateOperationInterval(OperationInterval operationInterval);

    void createOperationInterval(OperationInterval operationInterval);

    void deleteOperationInterval(long id);

    List<OperationInterval> getAll();

    void importOperationIntervalToNeo4j(OperationInterval operationInterval);

}
