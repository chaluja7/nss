package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RouteStopDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.RouteStop;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of RouteStopDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaRouteStopDao extends AbstractGenericJpaDao<RouteStop> implements RouteStopDao {

    public JpaRouteStopDao() {
        super(RouteStop.class);
    }

}
