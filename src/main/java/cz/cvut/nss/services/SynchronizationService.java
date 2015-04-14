package cz.cvut.nss.services;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface SynchronizationService {

    void deleteRouteById(long routeId);

    void deleteLineById(long lineId);

}
