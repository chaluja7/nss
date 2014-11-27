package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.StationDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Station;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of StationDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaStationDao extends AbstractGenericJpaDao<Station> implements StationDao {

}
