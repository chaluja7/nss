package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.Person;

/**
 * Interface for all implementations of PersonDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface PersonDao extends GenericDao<Person> {

    /**
     * find person by username
     * @param username username of person
     * @return person with given username
     */
    Person getPersonByUsername(String username);

}
