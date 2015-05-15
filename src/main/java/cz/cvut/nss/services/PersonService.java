package cz.cvut.nss.services;

import cz.cvut.nss.entities.Person;

import java.util.List;

/**
 * Common interface for all PersonService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface PersonService {

    /**
     * find person by id
     * @param id id of a person
     * @return person by id or null
     */
    Person getPerson(long id);

    /**
     * update person
     * @param person person to update
     * @return updated person
     */
    Person updatePerson(Person person);

    /**
     * persists person
     * @param person person to persist
     */
    void createPerson(Person person);

    /**
     * delete person
     * @param id id of person to delete
     */
    void deletePerson(long id);

    /**
     * find all persons
     * @return all persons
     */
    List<Person> getAll();


    /**
     * find person by username
     * @param username username of person
     * @return person with given username
     */
    Person getPersonByUsername(String username);

}
