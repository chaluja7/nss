package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.GoogleStationDao;
import cz.cvut.nss.entities.GoogleStation;
import cz.cvut.nss.services.GoogleStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Service
public class GoogleStationServiceImpl implements GoogleStationService {

    @Autowired
    protected GoogleStationDao googleStationDao;

    @Override
    @Transactional
    public void createGoogleStation(GoogleStation googleStation) {
        googleStationDao.create(googleStation);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleStation getGoogleStation(long id) {
        return googleStationDao.find(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        googleStationDao.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleStation getGoogleStationByStationId(String stationId) {
        return googleStationDao.findByStationId(stationId);
    }

    @Override
    @Transactional
    public GoogleStation updateGoogleStation(GoogleStation googleStation) {
        return googleStationDao.update(googleStation);
    }

    @Override
    @Transactional
    public void createGoogleStation(String stationId, String name, Double latitude, Double longitude, String parentStationId) {
        GoogleStation googleStation = new GoogleStation();
        googleStation.setStationId(stationId);
        googleStation.setName(name);
        googleStation.setLatitude(latitude);
        googleStation.setLongtitude(longitude);

        if(parentStationId != null) {
            GoogleStation parent = getGoogleStationByStationId(parentStationId);
            parent.addChildStation(googleStation);
        }

        createGoogleStation(googleStation);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleStation getByName(String name) {
        if(name == null) {
            return null;
        }

        return googleStationDao.findByName(name);
    }

}
