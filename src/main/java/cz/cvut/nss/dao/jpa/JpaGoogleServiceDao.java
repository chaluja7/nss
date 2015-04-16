package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.GoogleServiceDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.GoogleService;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Repository
public class JpaGoogleServiceDao extends AbstractGenericJpaDao<GoogleService> implements GoogleServiceDao {

    public JpaGoogleServiceDao() {
        super(GoogleService.class);
    }

    @Override
    public void deleteAll() {
        em.createNativeQuery("delete from google_services").executeUpdate();
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public GoogleService findByServiceId(Integer serviceId) {
        TypedQuery<GoogleService> query = em.createQuery("select g from GoogleService g where serviceId = :serviceId", GoogleService.class);
        query.setParameter("serviceId", serviceId);

        List<GoogleService> googleServiceList = query.getResultList();
        if(googleServiceList.size() == 1) {
            return  googleServiceList.get(0);
        }

        return null;
    }

}
