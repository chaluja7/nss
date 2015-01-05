package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.LineDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Line;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of LineDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaLineDao extends AbstractGenericJpaDao<Line> implements LineDao {

    public JpaLineDao() {
        super(Line.class);
    }

}
