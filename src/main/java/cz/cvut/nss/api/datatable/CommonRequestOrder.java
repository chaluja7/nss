package cz.cvut.nss.api.datatable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Datatables common request order.
 *
 * @author jakubchalupa
 * @since 07.04.15
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRequestOrder {

    @JsonProperty("column")
    private int columnIndex;

    @JsonProperty("dir")
    private String direction;

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
