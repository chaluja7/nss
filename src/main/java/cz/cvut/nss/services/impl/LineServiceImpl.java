package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.LineDao;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Implementation of LineService.
 */
@Service
public class LineServiceImpl implements LineService {

    @Autowired
    protected LineDao lineDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Line getLine(long id) {
        return lineDao.find(id);
    }

    @Override
    @Transactional
    public Line updateLine(Line line) {
        return lineDao.update(line);
    }

    @Override
    @Transactional
    public void createLine(Line line) {
        lineDao.create(line);
    }

    @Override
    @Transactional
    public void deleteLine(long id) {
        lineDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Line> getAll() {
        return lineDao.findAll();
    }

}
