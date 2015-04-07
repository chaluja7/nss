package cz.cvut.nss.api.datatable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Datatables filter request column search.
 *
 * @author jakubchalupa
 * @since 07.04.15
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRequestColumnSearch {

    @JsonProperty("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
