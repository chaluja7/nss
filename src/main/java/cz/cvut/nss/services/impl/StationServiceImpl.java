package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.StationDao;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of StationService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    protected StationDao stationDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Station getStation(long id) {
        return stationDao.find(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Station updateStation(Station station) {
        return stationDao.update(station);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createStation(Station station) {
        stationDao.create(station);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteStation(long id) {
        stationDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Station> getAll() {
        return stationDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Station> getAllByNamePattern(String pattern) {
        if(pattern == null || pattern.equals("")) {
            return new ArrayList<>();
        }

        return stationDao.getStationsByNamePattern(pattern);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Station getStationByTitle(String title) {
        return stationDao.getStationByTitle(title);
    }

}
