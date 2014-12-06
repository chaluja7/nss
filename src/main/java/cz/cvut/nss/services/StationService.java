package cz.cvut.nss.services;

import cz.cvut.nss.entities.Station;

import java.util.List;

/**
 * Common interface for all StationService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface StationService {

    /**
     * find station by id
     * @param id id of a station
     * @return station by id or null
     */
    public Station getStation(long id);

    /**
     * update station
     * @param station station to update
     * @return updated station
     */
    public Station updateStation(Station station);

    /**
     * persists station
     * @param station station to persist
     */
    public void createStation(Station station);

    /**
     * delete station
     * @param id id of station to delete
     */
    public void deleteStation(long id);

    /**
     * find all stations
     * @return all stations
     */
    public List<Station> getAll();

    /**
     * find all stations with pattern in name
     * @param pattern pattern of station name
     * @return all stations with pattern
     */
    public List<Station> getAllByNamePattern(String pattern);

    /**
     * find station by its title
     * @param title title of station
     * @return station with given title or null
     */
    public Station getStationByTitle(String title);

}
