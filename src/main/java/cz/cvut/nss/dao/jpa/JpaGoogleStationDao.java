package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.GoogleStationDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.GoogleStation;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Repository
public class JpaGoogleStationDao extends AbstractGenericJpaDao<GoogleStation> implements GoogleStationDao {

    public JpaGoogleStationDao() {
        super(GoogleStation.class);
    }

    @Override
    public void deleteAll() {
        em.createNativeQuery("delete from  google_stations").executeUpdate();
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public GoogleStation findByStationId(String stationId) {
        TypedQuery<GoogleStation> query = em.createQuery("select g from GoogleStation g where stationId = :stationId", GoogleStation.class);
        query.setParameter("stationId", stationId);

        List<GoogleStation> googleStationList = query.getResultList();
        if(googleStationList.size() == 1) {
            return  googleStationList.get(0);
        }

        return null;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public GoogleStation findByName(String name) {
        TypedQuery<GoogleStation> query = em.createQuery("select g from GoogleStation g where name = :name", GoogleStation.class);
        query.setParameter("name", name);

        List<GoogleStation> googleStationList = query.getResultList();
        if(googleStationList.size() == 1) {
            return  googleStationList.get(0);
        }

        return null;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<GoogleStation> findByNamePattern(String name) {
        TypedQuery<GoogleStation> query = em.createQuery("select g from GoogleStation g where name like :name", GoogleStation.class);
        query.setParameter("name", name + "%");

        return query.getResultList();
    }


}
