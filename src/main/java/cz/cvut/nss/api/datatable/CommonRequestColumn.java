package cz.cvut.nss.api.datatable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Datatables filter request column.
 *
 * @author jakubchalupa
 * @since 07.04.15
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRequestColumn {

    @JsonProperty("data")
    private String columnName;

    @JsonProperty("search")
    private CommonRequestColumnSearch search;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public CommonRequestColumnSearch getSearch() {
        return search;
    }

    public void setSearch(CommonRequestColumnSearch search) {
        this.search = search;
    }
}
