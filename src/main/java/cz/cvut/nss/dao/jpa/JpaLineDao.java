package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.LineDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Line;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of LineDao.
 */
@Repository
public class JpaLineDao extends AbstractGenericJpaDao<Line> implements LineDao {

}
