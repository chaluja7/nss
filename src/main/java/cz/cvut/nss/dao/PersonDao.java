package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.Person;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Interface for all implementations of PersonDao.
 */
public interface PersonDao extends GenericDao<Person> {

    /**
     * find person by username
     * @param username username of person
     * @return person with given username
     */
    Person getPersonByUsername(String username);

}
