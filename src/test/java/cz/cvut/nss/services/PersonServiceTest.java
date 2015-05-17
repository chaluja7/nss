package cz.cvut.nss.services;

import cz.cvut.nss.entities.Person;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Person service tests.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class PersonServiceTest extends AbstractServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testCreateAndGet() {
        Person person = preparePerson();

        Person retrieved = personService.getPerson(person.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(person.getUsername(), retrieved.getUsername());
    }

    @Test
    public void testUpdate() {
        Person person = preparePerson();

        Person retrieved = personService.getPerson(person.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setUsername("newName");
        personService.updatePerson(retrieved);

        Person updated = personService.getPerson(person.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getUsername(), updated.getUsername());
    }

    @Test
    public void testDelete() {
        Person person = preparePerson();

        Person retrieved = personService.getPerson(person.getId());
        Assert.assertNotNull(retrieved);

        personService.deletePerson(person.getId());
        Assert.assertNull(personService.getPerson(person.getId()));
    }

    private Person preparePerson() {
        Person person = new Person();
        person.setUsername("testUser");

        personService.createPerson(person);
        return person;
    }

}
