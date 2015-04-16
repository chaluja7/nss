package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.GoogleRouteDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.GoogleRoute;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Repository
public class JpaGoogleRouteDao extends AbstractGenericJpaDao<GoogleRoute> implements GoogleRouteDao {

    public JpaGoogleRouteDao() {
        super(GoogleRoute.class);
    }

    @Override
    public void deleteAll() {
        em.createNativeQuery("delete from google_routes").executeUpdate();
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public GoogleRoute findByRouteId(String routeId) {
        TypedQuery<GoogleRoute> query = em.createQuery("select g from GoogleRoute g where routeId = :routeId", GoogleRoute.class);
        query.setParameter("routeId", routeId);

        List<GoogleRoute> googleRouteList = query.getResultList();
        if(googleRouteList.size() == 1) {
            return  googleRouteList.get(0);
        }

        return null;
    }

}
