package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.api.datatable.CommonRequestColumn;
import cz.cvut.nss.api.datatable.CommonRequestOrder;
import cz.cvut.nss.dao.StationDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.utils.dto.IdsAndCountResult;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
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

    @Override
    public IdsAndCountResult getStationIdsByFilter(CommonRequest filter) {
        String where = " where 1 = 1";
        for(CommonRequestColumn column : filter.getColumns()) {
            if(column.getSearch() != null && !StringUtils.isEmpty(column.getSearch().getValue())) {
                switch(column.getColumnName()) {
                    case "title":
                        where += " and lower(s.title) like lower(:title)";
                        break;
                    case "name":
                        where += " and lower(s.name) like lower(:name)";
                        break;
                    case "region":
                        where += " and lower(r.name) like lower(:regionName)";
                        break;
                }
            }
        }

        if(filter.getSearch() != null && !StringUtils.isEmpty(filter.getSearch().getValue())) {
            where += " and (lower(s.title) like lower(:searchPattern) or lower(s.name) like lower(:searchPattern) or lower(r.name) like lower(:searchPattern))";
        }

        String from = " from stations s inner join regions r on s.region_id = r.id";
        String sqlQuery = "select s.id, (select count(s.id)" + from + where + ")" + from + where;

        if(filter.getOrder() != null && filter.getOrder().size() == 1) {
            CommonRequestOrder order = filter.getOrder().get(0);
            sqlQuery += " order by";

            switch (order.getColumnIndex()) {
                case 0:
                    sqlQuery += " s.title";
                    break;
                case 1:
                    sqlQuery += " s.name";
                    break;
                case 2:
                    sqlQuery += " r.name";
                    break;
            }

            sqlQuery += " " + order.getDirection();
        }

        Query query = em.createNativeQuery(sqlQuery);
        for(CommonRequestColumn column : filter.getColumns()) {
            if(column.getSearch() != null && !StringUtils.isEmpty(column.getSearch().getValue())) {
                switch(column.getColumnName()) {
                    case "title":
                        query.setParameter("title", "%" + column.getSearch().getValue() + "%");
                        break;
                    case "name":
                        query.setParameter("name", "%" + column.getSearch().getValue() + "%");
                        break;
                    case "region":
                        query.setParameter("regionName", "%" + column.getSearch().getValue() + "%");
                        break;
                }
            }
        }

        if(filter.getSearch() != null && !StringUtils.isEmpty(filter.getSearch().getValue())) {
            query.setParameter("searchPattern", "%" + filter.getSearch().getValue() + "%");
        }

        query.setFirstResult(filter.getNumberOfFirstRecord());
        query.setMaxResults(filter.getNumberOfRecordsToDisplay());

        List<Long> idsToReturn = new ArrayList<>();
        int totalCount = 0;
        List<Object[]> resultList = query.getResultList();
        for(Object[] objects : resultList) {
            idsToReturn.add(((BigInteger) objects[0]).longValue());
            totalCount = ((BigInteger) objects[1]).intValue();
        }

        return new IdsAndCountResult(idsToReturn, totalCount);
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<Station> getByIds(List<Long> stationIds, boolean preserveOrder) {
        if(stationIds == null || stationIds.isEmpty()) {
            return new ArrayList<>();
        }

        TypedQuery<Station> query = em.createQuery("select s from Station s where s.id in (:stationIds)", Station.class);
        query.setParameter("stationIds", stationIds);

        List<Station> stationList = query.getResultList();
        if(!preserveOrder) {
            return stationList;
        }

        List<Station> toReturn = new ArrayList<>();
        for(Long stationId : stationIds) {
            for(Station station : stationList) {
                if(station.getId().equals(stationId)) {
                    toReturn.add(station);
                    break;
                }
            }
        }

        return toReturn;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public int getCountAll() {
        TypedQuery<Long> query = em.createQuery("select count(s) from Station s", Long.class);
        return query.getSingleResult().intValue();
    }

}
