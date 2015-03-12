package cz.cvut.nss.wiew;

import cz.cvut.nss.SearchWrappers.FoundedPathsWrapper;
import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.StopService;
import cz.cvut.nss.utils.EosDateTimeUtils;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Backing bean for search results
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
@ManagedBean
@ViewScoped
public class SearchResultBB {

    @ManagedProperty(value = "#{stationServiceImpl}")
    private StationService stationService;

    @ManagedProperty(value = "#{searchServiceImpl}")
    private SearchService searchService;

    @ManagedProperty(value = "#{stopServiceImpl}")
    private StopService stopService;

    private String stationFromTitle;

    private String stationToTitle;

    private String departureDate;

    private String departureTime;

    private Station stationFrom;

    private Station stationTo;

    private Date departure;

    private boolean withoutTransfers;

    private boolean errorInputs = false;

    private List<FoundedPathsWrapper> foundResults;

    public void performSearch() {
        long l = System.currentTimeMillis();
        prepareAndValidateInputs();

        if(!errorInputs) {
            List<SearchResultWrapper> path = searchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departure, 1, withoutTransfers ? 0 : 2);

            foundResults = new ArrayList<>();
            for(SearchResultWrapper resultWrapper : path) {
                FoundedPathsWrapper wrapper = new FoundedPathsWrapper();

                wrapper.setStops(new ArrayList<Stop>());

                for(Long stopId : resultWrapper.getStops()) {
                    wrapper.getStops().add(stopService.getStop(stopId));
                }

                Stop from = wrapper.getStops().get(0);
                Stop to = wrapper.getStops().get(wrapper.getStops().size() - 1);

                Hours hours = Hours.hoursBetween(from.getDeparture(), to.getArrival());
                Minutes minutes = Minutes.minutesBetween(from.getDeparture(), to.getArrival());

                wrapper.setTravelTimeHours(hours.getHours());
                wrapper.setTravelTimeMinutes(minutes.getMinutes() % 60);

                foundResults.add(wrapper);
            }

        }

        long executionTime = System.currentTimeMillis() - l;
        int i = 0;
    }

    private void prepareAndValidateInputs() {
        stationFrom = stationService.getStationByTitle(stationFromTitle);
        stationTo = stationService.getStationByTitle(stationToTitle);

        if(stationFrom == null || stationTo == null) {
            errorInputs = true;
        }

        DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);
        try {
            departure = dateFormat.parse(departureDate + " " + departureTime);
        } catch (ParseException e) {
            errorInputs = true;
        }

    }

    public String getStationFromTitle() {
        return stationFromTitle;
    }

    public void setStationFromTitle(String stationFromTitle) {
        this.stationFromTitle = stationFromTitle;
    }

    public String getStationToTitle() {
        return stationToTitle;
    }

    public void setStationToTitle(String stationToTitle) {
        this.stationToTitle = stationToTitle;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public boolean isWithoutTransfers() {
        return withoutTransfers;
    }

    public void setWithoutTransfers(boolean withoutTransfers) {
        this.withoutTransfers = withoutTransfers;
    }

    public boolean isErrorInputs() {
        return errorInputs;
    }

    public List<FoundedPathsWrapper> getFoundResults() {
        return foundResults;
    }

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setStopService(StopService stopService) {
        this.stopService = stopService;
    }
}
