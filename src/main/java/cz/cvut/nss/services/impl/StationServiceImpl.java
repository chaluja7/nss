package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.StationDao;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Implementation of StationService.
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
    public Station updateStation(Station station) {
        return stationDao.update(station);
    }

    @Override
    @Transactional
    public void createStation(Station station) {
        stationDao.create(station);
    }

    @Override
    @Transactional
    public void deleteStation(long id) {
        stationDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Station> getAll() {
        return stationDao.findAll();
    }

}
