package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.api.datatable.CommonRequestColumn;
import cz.cvut.nss.api.datatable.CommonRequestOrder;
import cz.cvut.nss.dao.LineDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.utils.dto.IdsAndCountResult;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA implementation of LineDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaLineDao extends AbstractGenericJpaDao<Line> implements LineDao {

    public JpaLineDao() {
        super(Line.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IdsAndCountResult getLineIdsByFilter(CommonRequest filter) {
        String where = " where 1 = 1";
        for(CommonRequestColumn column : filter.getColumns()) {
            if(column.getSearch() != null && !StringUtils.isEmpty(column.getSearch().getValue())) {
                switch(column.getColumnName()) {
                    case "name":
                        where += " and lower(l.name) like lower(:name)";
                        break;
                    case "route":
                        where += " and lower(r.name) like lower(:routeName)";
                        break;
                    case "type":
                        where += " and lower(l.linetype) like lower(:type)";
                        break;
                }
            }
        }

        if(filter.getSearch() != null && !StringUtils.isEmpty(filter.getSearch().getValue())) {
            where += " and (lower(l.name) like lower(:searchPattern) or lower(r.name) like lower(:searchPattern) or lower(l.linetype) like lower(:searchPattern))";
        }

        String from = " from lines l inner join routes r on l.route_id = r.id";
        String sqlQuery = "select l.id, (select count(l.id)" + from + where + ")" + from + where;

        if(filter.getOrder() != null && filter.getOrder().size() == 1) {
            CommonRequestOrder order = filter.getOrder().get(0);
            sqlQuery += " order by";

            switch (order.getColumnIndex()) {
                case 0:
                    sqlQuery += " l.name";
                    break;
                case 1:
                    sqlQuery += " r.name";
                    break;
                case 2:
                    sqlQuery += " l.linetype";
                    break;
            }

            sqlQuery += " " + order.getDirection();
        }

        Query query = em.createNativeQuery(sqlQuery);
        for(CommonRequestColumn column : filter.getColumns()) {
            if(column.getSearch() != null && !StringUtils.isEmpty(column.getSearch().getValue())) {
                switch(column.getColumnName()) {
                    case "name":
                        query.setParameter("name", "%" + column.getSearch().getValue() + "%");
                        break;
                    case "route":
                        query.setParameter("routeName", "%" + column.getSearch().getValue() + "%");
                        break;
                    case "type":
                        query.setParameter("type", "%" + column.getSearch().getValue() + "%");
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
    public List<Line> getByIds(List<Long> lineIds, boolean preserveOrder) {
        if(lineIds == null || lineIds.isEmpty()) {
            return new ArrayList<>();
        }

        TypedQuery<Line> query = em.createQuery("select l from Line l where l.id in (:lineIds)", Line.class);
        query.setParameter("lineIds", lineIds);

        List<Line> lineList = query.getResultList();
        if(!preserveOrder) {
            return lineList;
        }

        List<Line> toReturn = new ArrayList<>();
        for(Long lineId : lineIds) {
            for(Line line : lineList) {
                if(line.getId().equals(lineId)) {
                    toReturn.add(line);
                    break;
                }
            }
        }

        return toReturn;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public int getCountAll() {
        TypedQuery<Long> query = em.createQuery("select count(l) from Line l", Long.class);
        return query.getSingleResult().intValue();
    }

}
