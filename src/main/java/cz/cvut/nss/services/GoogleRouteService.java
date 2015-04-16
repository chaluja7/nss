package cz.cvut.nss.services;

import cz.cvut.nss.entities.GoogleRoute;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleRouteService {

    void createGoogleRoute(GoogleRoute googleRoute);

    GoogleRoute getGoogleRoute(long id);

    void deleteAll();

    GoogleRoute getGoogleRouteByRouteId(String routeId);

    GoogleRoute updateGoogleRoute(GoogleRoute googleRoute);

    void createGoogleRoute(String routeId, String name, Integer agencyId, Integer routeType);

    List<GoogleRoute> findAll();

}
