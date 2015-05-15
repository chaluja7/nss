package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RideDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Ride;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

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

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<Ride> getByLineId(long lineId) {
        TypedQuery<Ride> query = em.createQuery("select r from Ride r where line.id = :lineId", Ride.class);
        query.setParameter("lineId", lineId);

        return query.getResultList();
    }

}
