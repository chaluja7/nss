package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.RegionDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Region;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of RegionDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaRegionDao extends AbstractGenericJpaDao<Region> implements RegionDao {

    public JpaRegionDao() {
        super(Region.class);
    }

}
