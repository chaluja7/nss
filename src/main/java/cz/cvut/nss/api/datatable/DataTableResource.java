package cz.cvut.nss.api.datatable;

import cz.cvut.nss.entities.AbstractEntity;

import java.util.List;

/**
 * Created by jakubchalupa on 24.11.14.
 *
 * Generic resource for data table purpose. It just wraps data to aa container.
 */
public class DataTableResource<T extends AbstractEntity> {

    private List<T> aaData;

    public DataTableResource() {

    }

    public DataTableResource(List<T> data) {
        setAaData(data);
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

}
