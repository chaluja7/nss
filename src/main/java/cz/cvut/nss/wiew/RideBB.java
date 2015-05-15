package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.*;
import cz.cvut.nss.services.*;
import org.joda.time.LocalTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.*;

/**
 * Backing bean for ride.
 *
 * @author jakubchalupa
 * @since 01.12.14
 */
@ManagedBean
@ViewScoped
public class RideBB {

    @ManagedProperty(value = "#{rideServiceImpl}")
    private RideService rideService;

    @ManagedProperty(value = "#{lineServiceImpl}")
    private LineService lineService;

    @ManagedProperty(value = "#{stationServiceImpl}")
    private StationService stationService;

    @ManagedProperty(value = "#{stopServiceImpl}")
    private StopService stopService;

    @ManagedProperty(value = "#{operationIntervalServiceImpl}")
    private OperationIntervalService operationIntervalService;

    private Long id;

    private Ride ride = new Ride();

    private Station newRideStop;

    private LocalTime newStopDeparture;

    private LocalTime newStopArrival;

    private Integer newStopOrder;

    private Long selectedLine;

    public void loadRide() {
        if(id != null) {
            ride = rideService.getRideLazyInitialized(id);
        }
    }

    public String saveRide() {
        rideService.createRide(ride);
        return "ride-list?faces-redirect=true";
    }

    public String updateRide() {
        rideService.updateRide(ride);
        return "ride-list?faces-redirect=true";
    }

    public String deleteRide() {
        rideService.deleteRide(ride.getId());
        return "ride-list?faces-redirect=true";
    }

    public String addStop() {
        if(id == null) {
            rideService.createRide(ride);
            id = ride.getId();
        }

        Stop stop = new Stop();
        stop.setStation(newRideStop);
        stop.setRide(ride);
        stop.setDeparture(newStopDeparture);
        stop.setArrival(newStopArrival);
        stop.setStopOrder(newStopOrder);

        stopService.createStop(stop);
        return "ride?id=" + id + "&faces-redirect=true";
    }

    public String deleteStop(long stopId) {
        stopService.deleteStop(stopId);
        return "ride?id=" + id + "&faces-redirect=true";
    }

    public Map<String, Object> getAllLines() {
        Map<String, Object> map = new TreeMap<>();
        for(Line line : lineService.getAll()) {
            map.put(line.getLineType().name() + " - " + line.getName(), line);
        }

        return map;
    }

    public Map<String, Object> getAllOperationIntervals() {
        Map<String, Object> map = new TreeMap<>();
        for(OperationInterval operationInterval : operationIntervalService.getAll()) {
            map.put(operationInterval.toString(), operationInterval);
        }

        return map;
    }

    public Map<String, Object> getAllAvailableStations() {
        Map<String, Object> map = new HashMap<>();
        for(Station station : stationService.getAll()) {
            if(!usedStations().contains(station)) {
                map.put(station.getName(), station);
            }
        }

        return map;
    }

    private List<Station> usedStations() {
        List<Station> stationList = new ArrayList<>();

        if(id == null) {
            return stationList;
        }

        for(Stop stop : ride.getStops()) {
            stationList.add(stop.getStation());
        }

        return stationList;
    }

    public Locale getLocale() {
        return new Locale("cs", "CZ");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Station getNewRideStop() {
        return newRideStop;
    }

    public void setNewRideStop(Station newRideStop) {
        this.newRideStop = newRideStop;
    }

    public LocalTime getNewStopDeparture() {
        return newStopDeparture;
    }

    public void setNewStopDeparture(LocalTime newStopDeparture) {
        this.newStopDeparture = newStopDeparture;
    }

    public LocalTime getNewStopArrival() {
        return newStopArrival;
    }

    public void setNewStopArrival(LocalTime newStopArrival) {
        this.newStopArrival = newStopArrival;
    }

    public Integer getNewStopOrder() {
        return newStopOrder;
    }

    public void setNewStopOrder(Integer newStopOrder) {
        this.newStopOrder = newStopOrder;
    }

    public Long getSelectedLine() {
        return selectedLine;
    }

    public void setSelectedLine(Long selectedLine) {
        this.selectedLine = selectedLine;
    }

    public void setRideService(RideService rideService) {
        this.rideService = rideService;
    }

    public void setLineService(LineService lineService) {
        this.lineService = lineService;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setStopService(StopService stopService) {
        this.stopService = stopService;
    }

    public void setOperationIntervalService(OperationIntervalService operationIntervalService) {
        this.operationIntervalService = operationIntervalService;
    }
}
