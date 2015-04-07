package cz.cvut.nss.api.datatable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Datatables filter request.
 *
 * @author jakubchalupa
 * @since 03.04.15
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRequest {

    @JsonProperty("start")
    private Integer numberOfFirstRecord;

    @JsonProperty("length")
    private Integer numberOfRecordsToDisplay;

    @JsonProperty("columns")
    private List<CommonRequestColumn> columns;

    @JsonProperty("order")
    private List<CommonRequestOrder> order;

    @JsonProperty("search")
    private CommonRequestSearch search;

    public Integer getNumberOfFirstRecord() {
        return numberOfFirstRecord;
    }

    public void setNumberOfFirstRecord(Integer numberOfFirstRecord) {
        this.numberOfFirstRecord = numberOfFirstRecord;
    }

    public Integer getNumberOfRecordsToDisplay() {
        return numberOfRecordsToDisplay;
    }

    public void setNumberOfRecordsToDisplay(Integer numberOfRecordsToDisplay) {
        this.numberOfRecordsToDisplay = numberOfRecordsToDisplay;
    }

    public List<CommonRequestColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<CommonRequestColumn> columns) {
        this.columns = columns;
    }

    public List<CommonRequestOrder> getOrder() {
        return order;
    }

    public void setOrder(List<CommonRequestOrder> order) {
        this.order = order;
    }

    public CommonRequestSearch getSearch() {
        return search;
    }

    public void setSearch(CommonRequestSearch search) {
        this.search = search;
    }
}
