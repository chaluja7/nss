package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RideDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Ride;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of RideDao.
 */
@Repository
public class JpaRideDao extends AbstractGenericJpaDao<Ride> implements RideDao {

}
