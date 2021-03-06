package cz.cvut.nss.dao.generics;

import cz.cvut.nss.entities.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Abstract JPA generic dao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public abstract class AbstractGenericJpaDao<T extends AbstractEntity> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    public AbstractGenericJpaDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T t) {
        em.persist(t);
    }

    @Override
    public T update(T t) {
        return em.merge(t);
    }

    @Override
    public T find(long id) {
        return em.find(type, id);
    }

    @Override
    public void delete(long id) {
        em.remove(em.getReference(type, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return em.createQuery("from " + type.getName()).getResultList();
    }
}
