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

    @ManagedProperty(value = "#{stopServiceImpl}")
    private StopService stopService;

    @ManagedProperty(value= "#{neo4jSearchService}")
    private SearchService neo4jSearchService;

    @ManagedProperty(value = "#{jdbcSearchService}")
    private SearchService jdbcSearchService;

    private String stationFromTitle;

    private String stationToTitle;

    private String departureOrArrivalDate;

    private String departureOrArrivalTime;

    private Station stationFrom;

    private Station stationTo;

    private Date departureOrArrival;

    private boolean withoutTransfers;

    private boolean withNeo4j;

    private String timeType;

    private boolean errorInputs = false;

    private List<FoundedPathsWrapper> foundResults;

    public void performSearch() {
        long l = System.currentTimeMillis();
        prepareAndValidateInputs();

        if(!errorInputs) {
            List<SearchResultWrapper> path;

            if(timeType.equals("departure")) {
                if(isWithNeo4j()) {
                    path = neo4jSearchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 12, withoutTransfers ? 0 : 3, -1);
                } else {
                    path = jdbcSearchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 12, withoutTransfers ? 0 : 3, -1);
                }
            } else {
                if(isWithNeo4j()) {
                    path = neo4jSearchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 12, withoutTransfers ? 0 : 3, 3);
                } else {
                    path = jdbcSearchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 12, withoutTransfers ? 0 : 3, 3);
                }
            }

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
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);
        try {
            departureOrArrival = dateFormat.parse(departureOrArrivalDate + " " + departureOrArrivalTime);
        } catch (ParseException e) {
            errorInputs = true;
            return;
        }

        if(timeType == null || (!timeType.equals("departure") && !timeType.equals("arrival"))) {
            errorInputs = true;
            return;
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

    public String getDepartureOrArrivalDate() {
        return departureOrArrivalDate;
    }

    public void setDepartureOrArrivalDate(String departureOrArrivalDate) {
        this.departureOrArrivalDate = departureOrArrivalDate;
    }

    public String getDepartureOrArrivalTime() {
        return departureOrArrivalTime;
    }

    public void setDepartureOrArrivalTime(String departureOrArrivalTime) {
        this.departureOrArrivalTime = departureOrArrivalTime;
    }

    public boolean isWithoutTransfers() {
        return withoutTransfers;
    }

    public void setWithoutTransfers(boolean withoutTransfers) {
        this.withoutTransfers = withoutTransfers;
    }

    public boolean isWithNeo4j() {
        return withNeo4j;
    }

    public void setWithNeo4j(boolean withNeo4j) {
        this.withNeo4j = withNeo4j;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
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

    public void setStopService(StopService stopService) {
        this.stopService = stopService;
    }

    public void setNeo4jSearchService(SearchService neo4jSearchService) {
        this.neo4jSearchService = neo4jSearchService;
    }

    public void setJdbcSearchService(SearchService jdbcSearchService) {
        this.jdbcSearchService = jdbcSearchService;
    }
}
