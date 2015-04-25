package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.GoogleTrip;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleTripDao extends GenericDao<GoogleTrip> {

    void deleteAll();

    GoogleTrip findByTripId(Integer tripId);

    GoogleTrip findOneByRouteId(String routeId);

    List<GoogleTrip> findAllForImport();


}
