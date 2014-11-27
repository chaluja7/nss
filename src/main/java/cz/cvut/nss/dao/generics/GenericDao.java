package cz.cvut.nss.dao.generics;

import cz.cvut.nss.entities.AbstractEntity;

import java.util.List;

/**
 * Common interface for all Dao implementation.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface GenericDao<T extends AbstractEntity> {

    /**
     * persist entity
     * @param t entity to persist
     */
    void create(T t);

    /**
     * update entity
     * @param t entity to update
     * @return updated entity
     */
    T update(T t);

    /**
     * find entity by id
     * @param id entity id
     * @return founded entity
     */
    T find(long id);

    /**
     * delete entity
     * @param id entity id (to delete)
     */
    void delete(long id);

    /**
     * will find all entities of a type
     * @return list of all entities by type
     */
    List<T> findAll();

}
