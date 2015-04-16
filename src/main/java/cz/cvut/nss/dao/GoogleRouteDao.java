package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.GoogleRoute;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleRouteDao extends GenericDao<GoogleRoute> {

    void deleteAll();

    GoogleRoute findByRouteId(String routeId);

}
