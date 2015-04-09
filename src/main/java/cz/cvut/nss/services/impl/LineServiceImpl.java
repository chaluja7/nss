package cz.cvut.nss.services.impl;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.dao.LineDao;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.services.LineService;
import cz.cvut.nss.services.neo4j.StopNeo4jService;
import cz.cvut.nss.utils.dto.EntitiesAndCountResult;
import cz.cvut.nss.utils.dto.IdsAndCountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of LineService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class LineServiceImpl implements LineService {

    @Autowired
    protected LineDao lineDao;

    @Autowired
    protected StopNeo4jService stopNeo4jService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Line getLine(long id) {
        return lineDao.find(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Line updateLine(Line line) {
        return lineDao.update(line);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createLine(Line line) {
        lineDao.create(line);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteLine(long id) {
        Line line = lineDao.find(id);
        if(line != null) {
            for(Ride ride : line.getRides()) {
                stopNeo4jService.deleteAllByRideId(ride.getId());
            }
            lineDao.delete(id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Line> getAll() {
        return lineDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public EntitiesAndCountResult<Line> getAllForDatatables(CommonRequest filter) {
        IdsAndCountResult idsAndCountResult = lineDao.getLineIdsByFilter(filter);
        EntitiesAndCountResult<Line> entitiesAndCountResult = new EntitiesAndCountResult<>();

        entitiesAndCountResult.setEntities(lineDao.getByIds(idsAndCountResult.getList(), true));
        entitiesAndCountResult.setCount(idsAndCountResult.getCount());

        return entitiesAndCountResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getCountAll() {
        return lineDao.getCountAll();
    }

}
