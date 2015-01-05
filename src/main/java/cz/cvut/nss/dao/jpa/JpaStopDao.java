package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.StopDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Stop;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of StopDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaStopDao extends AbstractGenericJpaDao<Stop> implements StopDao {

    public JpaStopDao() {
        super(Stop.class);
    }

}
