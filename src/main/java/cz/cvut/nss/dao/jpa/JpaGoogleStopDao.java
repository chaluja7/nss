package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.GoogleStopDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.GoogleStop;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Repository
public class JpaGoogleStopDao extends AbstractGenericJpaDao<GoogleStop> implements GoogleStopDao {

    public JpaGoogleStopDao() {
        super(GoogleStop.class);
    }

    @Override
    public void deleteAll() {
        em.createNativeQuery("delete from google_stops").executeUpdate();
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<GoogleStop> findByTripId(Integer tripId) {
        TypedQuery<GoogleStop> query = em.createQuery("select g from GoogleStop g where tripId = :tripId order by stop_order", GoogleStop.class);
        query.setParameter("tripId", tripId);

        return query.getResultList();
    }


}
