package cz.cvut.nss.wiew;

import cz.cvut.nss.SearchWrappers.FoundedPathsWrapper;
import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.StopService;
import cz.cvut.nss.utils.EosDateTimeUtils;
import org.joda.time.DateTime;
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

    private int maxTransfers;

    private boolean withNeo4j;

    private String timeType;

    private int millisOfDepartureDay;

    private String departureDay;

    private String arrivalDay;

    private boolean errorInputs = false;

    private List<FoundedPathsWrapper> foundResults;

    public void performSearch() {
        long l = System.currentTimeMillis();
        prepareAndValidateInputs();

        if(!errorInputs) {
            List<SearchResultWrapper> path;
            int maxNumberOfTransfers = withoutTransfers ? 0 : maxTransfers;

            if(timeType.equals("departure")) {
                if(isWithNeo4j()) {
                    path = neo4jSearchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 4, maxNumberOfTransfers, -1);
                } else {
                    path = jdbcSearchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 4, maxNumberOfTransfers, -1);
                }
            } else {
                if(isWithNeo4j()) {
                    path = neo4jSearchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 4, maxNumberOfTransfers, -1);
                } else {
                    path = jdbcSearchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 4, maxNumberOfTransfers, -1);
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

                //from.getArrival().

                //todo hours and minutes
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

    public void findNextRides() {
        DateTime newDateTime;
        if(foundResults != null && !foundResults.isEmpty()) {
            //jako bernou minci beru posledni nalazeny spoj a cas odjezdu z vychozi stanice
            FoundedPathsWrapper lastFoundedPathWrapper = foundResults.get(foundResults.size() - 1);
            Stop firstStop = lastFoundedPathWrapper.getStops().get(0);
            //todo
            DateTime dateTime = firstStop.getDeparture().toDateTimeToday();
            //DateTime dateTime = firstStop.getDeparture().toDateTime();
            newDateTime = dateTime.plusMillis(1);
            timeType = "departure";
        } else {
            DateTime dateTime = new DateTime(departureOrArrival);
            newDateTime = dateTime.plusHours(9);
        }

        departureOrArrival = newDateTime.toDate();
        departureOrArrivalDate = newDateTime.toString(EosDateTimeUtils.datePattern);
        departureOrArrivalTime = newDateTime.toString(EosDateTimeUtils.timePattern);
    }

    public void findPrevRides() {
        DateTime newDateTime;
        if(foundResults != null && !foundResults.isEmpty()) {
            //jako bernou minci beru prvni nalazeny spoj a cas prijezdu do cilove stanice
            FoundedPathsWrapper firstFoundedPathWrapper = foundResults.get(0);
            Stop lastStop = firstFoundedPathWrapper.getStops().get(firstFoundedPathWrapper.getStops().size() - 1);
            //todo
            DateTime dateTime = lastStop.getArrival().toDateTimeToday();
            //DateTime dateTime = lastStop.getArrival().toDateTime();
            newDateTime = dateTime.minusMillis(1);
            timeType = "arrival";
        } else {
            DateTime dateTime = new DateTime(departureOrArrival);
            newDateTime = dateTime.minusHours(9);
        }

        departureOrArrival = newDateTime.toDate();
        departureOrArrivalDate = newDateTime.toString(EosDateTimeUtils.datePattern);
        departureOrArrivalTime = newDateTime.toString(EosDateTimeUtils.timePattern);
    }

    private void prepareAndValidateInputs() {
        stationFrom = stationService.getStationByName(stationFromTitle);
        stationTo = stationService.getStationByName(stationToTitle);

        if(stationFrom == null || stationTo == null) {
            errorInputs = true;
            return;
        }

        if(departureOrArrival == null) {
            DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);
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

        if(timeType.equals("departure")) {
            departureDay = dt.toString(EosDateTimeUtils.datePattern);
            arrivalDay = dt.plusDays(1).toString(EosDateTimeUtils.datePattern);
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

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setStopService(StopService stopService) {
        this.stopService = stopService;
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

    public void setNeo4jSearchService(SearchService neo4jSearchService) {
        this.neo4jSearchService = neo4jSearchService;
    }

    public void setJdbcSearchService(SearchService jdbcSearchService) {
        this.jdbcSearchService = jdbcSearchService;
    }
}
