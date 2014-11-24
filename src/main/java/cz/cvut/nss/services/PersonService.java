package cz.cvut.nss.services;

import cz.cvut.nss.entities.Person;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Common interface for all PersonService implementations.
 */
public interface PersonService {

    /**
     * find person by id
     * @param id id of a person
     * @return person by id or null
     */
    public Person getPerson(long id);

    /**
     * update person
     * @param person person to update
     * @return updated person
     */
    public Person updatePerson(Person person);

    /**
     * persists person
     * @param person person to persist
     */
    public void createPerson(Person person);

    /**
     * delete person
     * @param id id of person to delete
     */
    public void deletePerson(long id);

    /**
     * find all persons
     * @return all persons
     */
    public List<Person> getAll();


    /**
     * find person by username
     * @param username username of person
     * @return person with given username
     */
    public Person getPersonByUsername(String username);

}
