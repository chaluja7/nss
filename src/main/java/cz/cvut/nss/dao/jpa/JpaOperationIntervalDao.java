package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.OperationIntervalDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.OperationInterval;
import org.springframework.stereotype.Repository;

/**
 * @author jakubchalupa
 * @since 17.04.15
 */
@Repository
public class JpaOperationIntervalDao extends AbstractGenericJpaDao<OperationInterval> implements OperationIntervalDao {

    public JpaOperationIntervalDao() {
        super(OperationInterval.class);
    }

}
