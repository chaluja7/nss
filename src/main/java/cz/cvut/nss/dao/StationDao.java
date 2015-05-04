package cz.cvut.nss.dao;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.utils.dto.IdsAndCountResult;

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

    /**
     * find station with given name
     * @param name name of station
     * @return station with given name or null
     */
    Station getStationByName(String name);

    /**
     * find station ids and total number of filtered stations
     * @param filter filter
     * @return list ids ant total count of filtered
     */
    IdsAndCountResult getStationIdsByFilter(CommonRequest filter);

    /**
     * find stations by id
     * @param stationIds station ids
     * @param preserveOrder if order should be as the one from list
     * @return list station
     */
    List<Station> getByIds(List<Long> stationIds, boolean preserveOrder);

    /**
     * get count of all stations
     * @return count of all stations
     */
    int getCountAll();

    List<Station> getAllWithOrder(String orderColumn);

}
