package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.PersonDao;
import cz.cvut.nss.entities.Person;
import cz.cvut.nss.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of PersonService.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    protected PersonDao personDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Person getPerson(long id) {
        return personDao.find(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Person updatePerson(Person person) {
        return personDao.update(person);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createPerson(Person person) {
        personDao.create(person);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deletePerson(long id) {
        personDao.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Person> getAll() {
        return personDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Person getPersonByUsername(String username) {
        return personDao.getPersonByUsername(username);
    }

}
