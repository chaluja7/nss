package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.StopDao;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of StopService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class StopServiceImpl implements StopService {

    @Autowired
    protected StopDao stopDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Stop getStop(long id) {
        return stopDao.find(id);
    }

    @Override
    @Transactional
    public Stop updateStop(Stop stop) {
        return stopDao.update(stop);
    }

    @Override
    @Transactional
    public void createStop(Stop stop) {
        stopDao.create(stop);
    }

    @Override
    @Transactional
    public void deleteStop(long id) {
        stopDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Stop> getAll() {
        return stopDao.findAll();
    }

}
