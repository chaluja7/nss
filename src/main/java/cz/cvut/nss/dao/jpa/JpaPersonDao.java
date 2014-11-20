package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.PersonDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Person;
import org.springframework.stereotype.Repository;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of PersonDao.
 */
@Repository
public class JpaPersonDao extends AbstractGenericJpaDao<Person> implements PersonDao {

}
