package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RideDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Ride;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of RideDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaRideDao extends AbstractGenericJpaDao<Ride> implements RideDao {

    public JpaRideDao() {
        super(Ride.class);
    }

}
