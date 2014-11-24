package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.PersonDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * JPA implementation of PersonDao.
 */
@Repository
public class JpaPersonDao extends AbstractGenericJpaDao<Person> implements PersonDao {

    @Override
    @SuppressWarnings("JpaQlInspection")
    public Person getPersonByUsername(String username) {
        TypedQuery<Person> query = em.createQuery("select p from Person p where username = :username", Person.class);
        query.setParameter("username", username);

        if(query.getResultList().size() == 1) {
            return query.getResultList().get(0);
        }

        return null;
    }
}
