package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.GoogleTripDao;
import cz.cvut.nss.entities.GoogleTrip;
import cz.cvut.nss.services.GoogleTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jakubchalupa
 * @since 15.04.15
 */
@Service
public class GoogleTripServiceImpl implements GoogleTripService {

    @Autowired
    protected GoogleTripDao googleTripDao;

    @Override
    @Transactional
    public void createGoogleTrip(GoogleTrip googleTrip) {
        googleTripDao.create(googleTrip);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleTrip getGoogleTrip(long id) {
        return googleTripDao.find(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        googleTripDao.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleTrip getGoogleTripByTripId(Integer tripId) {
        return googleTripDao.findByTripId(tripId);
    }

    @Override
    @Transactional
    public GoogleTrip updateGoogleTrip(GoogleTrip googleTrip) {
        return googleTripDao.update(googleTrip);
    }

    @Override
    @Transactional
    public void createGoogleTrip(Integer tripId, String routeId, Integer serviceId) {
        GoogleTrip googleTrip = new GoogleTrip();
        googleTrip.setTripId(tripId);
        googleTrip.setRouteId(routeId);
        googleTrip.setServiceId(serviceId);

        createGoogleTrip(googleTrip);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleTrip getOneByRouteId(String routeId) {
        return googleTripDao.findOneByRouteId(routeId);
    }

}
