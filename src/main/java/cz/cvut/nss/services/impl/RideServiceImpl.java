package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.RideDao;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of RideService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class RideServiceImpl implements RideService {

    @Autowired
    protected RideDao rideDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Ride getRide(long id) {
        return rideDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Ride getRideLazyInitialized(long id) {
        Ride ride = rideDao.find(id);
        if(ride != null) {
            ride.getStops().size();
        }

        return ride;
    }

    @Override
    @Transactional
    public Ride updateRide(Ride ride) {
        return rideDao.update(ride);
    }

    @Override
    @Transactional
    public void createRide(Ride ride) {
        rideDao.create(ride);
    }

    @Override
    @Transactional
    public void deleteRide(long id) {
        rideDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Ride> getAll() {
        return rideDao.findAll();
    }

}
