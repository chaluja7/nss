package cz.cvut.nss.services;

import cz.cvut.nss.entities.OperationInterval;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface SynchronizationService {

    void deleteRouteById(long routeId);

    void deleteLineById(long lineId);

    void saveOperationInterval(OperationInterval operationInterval);

    OperationInterval updateOperationInterval(OperationInterval operationInterval);

    void deleteOperationIntervalByOperationIntervalId(long operationIntervalId);

}
