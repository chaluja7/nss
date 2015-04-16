package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.GoogleStop;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleStopDao extends GenericDao<GoogleStop> {

    void deleteAll();

    List<GoogleStop> findByTripId(Integer tripId);

}
