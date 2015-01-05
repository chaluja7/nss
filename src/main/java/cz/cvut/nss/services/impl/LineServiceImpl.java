package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.LineDao;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.services.LineService;
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
        lineDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Line> getAll() {
        return lineDao.findAll();
    }

}
