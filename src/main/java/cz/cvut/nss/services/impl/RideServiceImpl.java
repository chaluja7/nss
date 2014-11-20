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
 * Created by jakubchalupa on 20.11.14.
 *
 * Implementation of RideService.
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
