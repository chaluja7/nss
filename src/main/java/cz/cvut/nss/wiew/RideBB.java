package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.LineService;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.StopService;
import org.joda.time.LocalDateTime;

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

    private Long id;

    private Ride ride = new Ride();

    private Station newRideStop;

    private LocalDateTime newStopDeparture;

    private LocalDateTime newStopArrival;

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
        //todo
        stop.setDeparture(null);
        stop.setArrival(null);
//        stop.setDeparture(newStopDeparture);
//        stop.setArrival(newStopArrival);

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

    public Map<String, Object> getAllAvailableStations() {
        Map<String, Object> map = new HashMap<>();
        for(Station station : stationService.getAll()) {
            if(!usedStations().contains(station)) {
                map.put(station.getTitle(), station);
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

    public LocalDateTime getNewStopDeparture() {
        return newStopDeparture;
    }

    public void setNewStopDeparture(LocalDateTime newStopDeparture) {
        this.newStopDeparture = newStopDeparture;
    }

    public LocalDateTime getNewStopArrival() {
        return newStopArrival;
    }

    public void setNewStopArrival(LocalDateTime newStopArrival) {
        this.newStopArrival = newStopArrival;
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
}
