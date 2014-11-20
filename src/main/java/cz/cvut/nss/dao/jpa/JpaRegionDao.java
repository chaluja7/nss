package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RegionDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Region;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of RegionDao.
 */
@Repository
public class JpaRegionDao extends AbstractGenericJpaDao<Region> implements RegionDao {

}
