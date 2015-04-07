package cz.cvut.nss.utils.dto;

import java.util.List;

/**
 * Helping result to aggregate list of results and total count of filtered results.
 *
 * @author jakubchalupa
 * @since 07.04.15
 */
public class EntitiesAndCountResult<E> {

    private List<E> entities;

    private int count;

    public List<E> getEntities() {
        return entities;
    }

    public void setEntities(List<E> entities) {
        this.entities = entities;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
