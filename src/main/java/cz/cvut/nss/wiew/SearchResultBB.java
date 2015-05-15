package cz.cvut.nss.wiew;

import cz.cvut.nss.SearchWrappers.FoundedPathsWrapper;
import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.StopService;
import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.DateTime;

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

    private static final int SEARCH_INTERVAL_HOURS = 6;

    private static final int MAX_NUMBER_OF_RESULTS = 3;

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

    private int maxTransfers;

    private boolean withNeo4j;

    private String timeType;

    private int millisOfDepartureDay;

    private String departureDay;

    private String arrivalDay;

    private boolean errorInputs = false;

    private List<FoundedPathsWrapper> foundResults;

    public void performSearch() {
        prepareAndValidateInputs();

        if(!errorInputs) {
            List<SearchResultWrapper> path;
            int maxNumberOfTransfers = withoutTransfers ? 0 : maxTransfers;

            long l = System.currentTimeMillis();
            if(timeType.equals("departure")) {
                if(isWithNeo4j()) {
                    path = neo4jSearchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival,
                            SEARCH_INTERVAL_HOURS, maxNumberOfTransfers, MAX_NUMBER_OF_RESULTS);
                } else {
                    path = jdbcSearchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival,
                            SEARCH_INTERVAL_HOURS, maxNumberOfTransfers, MAX_NUMBER_OF_RESULTS);
                }
            } else {
                if(isWithNeo4j()) {
                    path = neo4jSearchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival,
                            SEARCH_INTERVAL_HOURS, maxNumberOfTransfers, MAX_NUMBER_OF_RESULTS);
                } else {
                    path = jdbcSearchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival,
                            SEARCH_INTERVAL_HOURS, maxNumberOfTransfers, MAX_NUMBER_OF_RESULTS);
                }
            }

            l = System.currentTimeMillis() - l;
            foundResults = new ArrayList<>();
            for(SearchResultWrapper resultWrapper : path) {
                FoundedPathsWrapper wrapper = new FoundedPathsWrapper();
                wrapper.setStops(new ArrayList<Stop>());

                for(Long stopId : resultWrapper.getStops()) {
                    wrapper.getStops().add(stopService.getStop(stopId));
                }

                int travelTimeMillis = (int) resultWrapper.getTravelTime();
                int travelTimeSeconds = travelTimeMillis / 1000;
                int travelTimeMinutes = travelTimeSeconds / 60;
                int travelTimeHours = travelTimeMinutes / 60;

                wrapper.setTravelTimeHours(travelTimeHours);
                wrapper.setTravelTimeMinutes(travelTimeMinutes % 60);
                foundResults.add(wrapper);
            }
        }

    }

    private void prepareAndValidateInputs() {
        stationFrom = stationService.getStationByName(stationFromTitle);
        stationTo = stationService.getStationByName(stationToTitle);

        if(stationFrom == null || stationTo == null) {
            errorInputs = true;
            return;
        }

        if(departureOrArrival == null) {
            DateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.DATE_TIME_PATTERN);
            try {
                departureOrArrival = dateFormat.parse(departureOrArrivalDate + " " + departureOrArrivalTime);
            } catch (ParseException e) {
                errorInputs = true;
                return;
            }
        }

        DateTime dt = new DateTime(departureOrArrival);
        millisOfDepartureDay = dt.getMillisOfDay();

        if(timeType == null || (!timeType.equals("departure") && !timeType.equals("arrival"))) {
            errorInputs = true;
            return;
        }

        if(timeType.equals("departure") || timeType.equals("arrival")) {
            departureDay = dt.toString(DateTimeUtils.DATE_PATTERN);
            arrivalDay = dt.plusDays(1).toString(DateTimeUtils.DATE_PATTERN);
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

    public int getMaxTransfers() {
        return maxTransfers;
    }

    public void setMaxTransfers(int maxTransfers) {
        this.maxTransfers = maxTransfers;
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

    public int getMillisOfDepartureDay() {
        return millisOfDepartureDay;
    }

    public void setMillisOfDepartureDay(int millisOfDepartureDay) {
        this.millisOfDepartureDay = millisOfDepartureDay;
    }

    public String getDepartureDay() {
        return departureDay;
    }

    public String getArrivalDay() {
        return arrivalDay;
    }

    public void setDepartureDay(String departureDay) {
        this.departureDay = departureDay;
    }

    public void setArrivalDay(String arrivalDay) {
        this.arrivalDay = arrivalDay;
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
