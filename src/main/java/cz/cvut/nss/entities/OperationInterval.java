package cz.cvut.nss.entities;

import cz.cvut.nss.utils.DateTimeUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OperationInterval entity.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "operation_intervals")
public class OperationInterval extends AbstractEntity {

    @Column
    private Boolean monday;

    @Column
    private Boolean tuesday;

    @Column
    private Boolean wednesday;

    @Column
    private Boolean thursday;

    @Column
    private Boolean friday;

    @Column
    private Boolean saturday;

    @Column
    private Boolean sunday;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "operationInterval")
    private List<Ride> rides;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Ride> getRides() {
        if(rides == null) {
            rides = new ArrayList<>();
        }

        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public void addRide(Ride ride) {
        if(!getRides().contains(ride)) {
            getRides().add(ride);
        }

        ride.setOperationInterval(this);
    }

    @Override
    public String toString() {
        String oiName = getId() + " (";
        oiName += getStartDate().toString(DateTimeUtils.DATE_PATTERN) + " - " + getEndDate().toString(DateTimeUtils.DATE_PATTERN);
        oiName += ") ";
        if(getMonday()) {
            oiName += "PO|";
        }
        if(getTuesday()) {
            oiName += "ÚT|";
        }
        if(getWednesday()) {
            oiName += "ST|";
        }
        if(getThursday()) {
            oiName += "ČT|";
        }
        if(getFriday()) {
            oiName += "PÁ|";
        }
        if(getSaturday()) {
            oiName += "SO|";
        }
        if(getSunday()) {
            oiName += "NE|";
        }

        return oiName;
    }
}
