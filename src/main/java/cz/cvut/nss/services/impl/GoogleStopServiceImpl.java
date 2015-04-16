package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.GoogleStopDao;
import cz.cvut.nss.entities.GoogleStop;
import cz.cvut.nss.services.GoogleStopService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 15.04.15
 */
@Service
public class GoogleStopServiceImpl implements GoogleStopService {

    @Autowired
    protected GoogleStopDao googleStopDao;

    @Override
    @Transactional
    public void createGoogleStop(GoogleStop googleStop) {
        googleStopDao.create(googleStop);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleStop getGoogleStop(long id) {
        return googleStopDao.find(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        googleStopDao.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<GoogleStop> getGoogleStopsByTripId(Integer tripId) {
        return googleStopDao.findByTripId(tripId);
    }


    @Override
    @Transactional
    public GoogleStop updateGoogleStop(GoogleStop googleStop) {
        return googleStopDao.update(googleStop);
    }

    @Override
    @Transactional
    public void createGoogleStop(String stationId, Integer tripId, Integer order, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        GoogleStop googleStop = new GoogleStop();
        googleStop.setStationId(stationId);
        googleStop.setTripId(tripId);
        googleStop.setOrder(order);
        googleStop.setArrivalTime(arrivalTime);
        googleStop.setDepartureTime(departureTime);

        createGoogleStop(googleStop);
    }

}
