package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.Station;

import java.util.List;

/**
 * Interface for all implementations of StationDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface StationDao extends GenericDao<Station> {

    /**
     * find all stations with pattern in name
     * @param pattern pattern of name
     * @return list of stations with pattern in name
     */
    List<Station> getStationsByNamePattern(String pattern);

    /**
     * find station with given title
     * @param title title of station
     * @return station with given title or null
     */
    Station getStationByTitle(String title);

}
