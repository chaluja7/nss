package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.StationDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA implementation of StationDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaStationDao extends AbstractGenericJpaDao<Station> implements StationDao {

    public JpaStationDao() {
        super(Station.class);
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<Station> getStationsByNamePattern(String pattern) {
        TypedQuery<Station> query = em.createQuery("select s from Station s where lower(name) like :pattern", Station.class);
        query.setParameter("pattern", "%" + pattern.toLowerCase() + "%");

        return query.getResultList();
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public Station getStationByTitle(String title) {
        TypedQuery<Station> query = em.createQuery("select s from Station s where lower(title) = :title", Station.class);
        query.setParameter("title", title.toLowerCase());

        if(query.getResultList().size() == 1) {
            return query.getResultList().get(0);
        }

        return null;
    }

}
