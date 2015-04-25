package cz.cvut.nss.entities.neo4j;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author jakubchalupa
 * @since 18.04.15
 */
@NodeEntity
public class OperationIntervalNode {

    public static final String FROM_DATE_PROPERTY = "fromDateInMillis";

    public static final String TO_DATE_PROPERTY = "toDateInMillis";

    @GraphId
    private Long id;

    @Indexed(unique = true)
    @GraphProperty
    private Long operationIntervalId;

    @Indexed
    @GraphProperty
    private Boolean monday;

    @Indexed
    @GraphProperty
    private Boolean tuesday;

    @Indexed
    @GraphProperty
    private Boolean wednesday;

    @Indexed
    @GraphProperty
    private Boolean thursday;

    @Indexed
    @GraphProperty
    private Boolean friday;

    @Indexed
    @GraphProperty
    private Boolean saturday;

    @Indexed
    @GraphProperty
    private Boolean sunday;

    @Indexed
    @GraphProperty
    private Long fromDateInMillis;

    @Indexed
    @GraphProperty
    private Long toDateInMillis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationIntervalId() {
        return operationIntervalId;
    }

    public void setOperationIntervalId(Long operationIntervalId) {
        this.operationIntervalId = operationIntervalId;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Long getFromDateInMillis() {
        return fromDateInMillis;
    }

    public void setFromDateInMillis(Long fromDateInMillis) {
        this.fromDateInMillis = fromDateInMillis;
    }

    public Long getToDateInMillis() {
        return toDateInMillis;
    }

    public void setToDateInMillis(Long toDateInMillis) {
        this.toDateInMillis = toDateInMillis;
    }
}
