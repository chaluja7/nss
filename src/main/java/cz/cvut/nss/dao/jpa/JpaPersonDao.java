package cz.cvut.nss.dao.jpa;

import cz.cvut.nss.dao.PersonDao;
import cz.cvut.nss.dao.generics.AbstractGenericJpaDao;
import cz.cvut.nss.entities.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * JPA implementation of PersonDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Repository
public class JpaPersonDao extends AbstractGenericJpaDao<Person> implements PersonDao {

    public JpaPersonDao() {
        super(Person.class);
    }

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
