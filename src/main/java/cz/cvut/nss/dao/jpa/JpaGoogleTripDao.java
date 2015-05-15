package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.GoogleTripDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.GoogleTrip;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
@Repository
public class JpaGoogleTripDao extends AbstractGenericJpaDao<GoogleTrip> implements GoogleTripDao {

    public JpaGoogleTripDao() {
        super(GoogleTrip.class);
    }

    @Override
    public void deleteAll() {
        em.createNativeQuery("delete from google_trips").executeUpdate();
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public GoogleTrip findByTripId(Integer tripId) {
        TypedQuery<GoogleTrip> query = em.createQuery("select g from GoogleTrip g where tripId = :tripId", GoogleTrip.class);
        query.setParameter("tripId", tripId);

        List<GoogleTrip> googleTripList = query.getResultList();
        if(googleTripList.size() == 1) {
            return  googleTripList.get(0);
        }

        return null;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public GoogleTrip findOneByRouteId(String routeId) {
        TypedQuery<GoogleTrip> query = em.createQuery("select g from GoogleTrip g where routeId = :routeId", GoogleTrip.class);
        query.setParameter("routeId", routeId);
        query.setMaxResults(1);

        List<GoogleTrip> googleTripList = query.getResultList();
        if(googleTripList.size() == 1) {
            return  googleTripList.get(0);
        }

        return null;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<GoogleTrip> findAllForImport() {
        String sql = "select id, routeid, serviceid, tripid, agencyid from google_trips where agencyid = 1 ";
        sql += "and serviceid in (select serviceid from google_services where '2015-07-05' between startdate and enddate and '2015-08-31' between startdate and enddate) and id % 20 <> 0 and id % 5 = 0";

//        String sql = "select id, routeid, serviceid, tripid, agencyid from google_trips where agencyid = 10 ";
//        sql += "and serviceid in (select serviceid from google_services where '2015-07-05' between startdate and enddate and '2015-08-31' between startdate and enddate)";

        Query query2 = em.createNativeQuery(sql, GoogleTrip.class);
        return query2.getResultList();
    }

}
