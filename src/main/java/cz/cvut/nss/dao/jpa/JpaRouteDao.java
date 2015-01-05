package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RouteDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Route;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of RouteDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaRouteDao extends AbstractGenericJpaDao<Route> implements RouteDao {

    public JpaRouteDao() {
        super(Route.class);
    }

}
