package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.GoogleStation;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleStationDao extends GenericDao<GoogleStation> {

    void deleteAll();

    GoogleStation findByStationId(String stationId);

    GoogleStation findByName(String name);

}
