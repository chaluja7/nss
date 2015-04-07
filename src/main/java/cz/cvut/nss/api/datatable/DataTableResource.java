package cz.cvut.nss.api.datatable;

import java.util.List;

/**
 *  Generic resource for data table purpose. It just wraps data to aa container.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public class DataTableResource<T> {

    private List<T> aaData;

    private int recordsFiltered;

    private int recordsTotal;

    public DataTableResource() {

    }

    public DataTableResource(List<T> data) {
        setAaData(data);
    }

    public DataTableResource(List<T> data, int recordsFiltered) {
        setAaData(data);
        setRecordsFiltered(recordsFiltered);
    }

    public DataTableResource(List<T> data, int recordsFiltered, int recordsTotal) {
        setAaData(data);
        setRecordsFiltered(recordsFiltered);
        setRecordsTotal(recordsTotal);
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
}
