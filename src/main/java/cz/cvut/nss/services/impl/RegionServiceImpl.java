package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.RegionDao;
import cz.cvut.nss.entities.Region;
import cz.cvut.nss.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Implementation of RegionService.
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    protected RegionDao regionDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Region getRegion(long id) {
        return regionDao.find(id);
    }

    @Override
    @Transactional
    public Region updateRegion(Region region) {
        return regionDao.update(region);
    }

    @Override
    @Transactional
    public void createRegion(Region region) {
        regionDao.create(region);
    }

    @Override
    @Transactional
    public void deleteRegion(long id) {
        regionDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Region> getAll() {
        return regionDao.findAll();
    }

}
