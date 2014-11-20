package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.StationDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Station;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of StationDao.
 */
@Repository
public class JpaStationDao extends AbstractGenericJpaDao<Station> implements StationDao {

}
