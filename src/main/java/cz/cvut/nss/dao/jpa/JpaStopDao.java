package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.StopDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Stop;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of StopDao.
 */
@Repository
public class JpaStopDao extends AbstractGenericJpaDao<Stop> implements StopDao {

}
