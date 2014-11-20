package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RouteDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Route;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of RouteDao.
 */
@Repository
public class JpaRouteDao extends AbstractGenericJpaDao<Route> implements RouteDao {

}
