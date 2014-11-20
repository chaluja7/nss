package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RouteStopDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.RouteStop;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of RouteStopDao.
 */
@Repository
public class JpaRouteStopDao extends AbstractGenericJpaDao<RouteStop> implements RouteStopDao {

}
