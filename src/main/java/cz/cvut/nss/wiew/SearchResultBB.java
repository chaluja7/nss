package cz.cvut.nss.wiew;

import cz.cvut.nss.SearchWrappers.FoundedPathsWrapper;
import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.entities.neo4j.StationNode;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.StopService;
import cz.cvut.nss.services.neo4j.StationNeo4jService;
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

    @ManagedProperty(value = "#{stationNeo4jServiceImpl}")
    private StationNeo4jService stationNeo4jService;

    private String stationFromTitle;

    private String stationToTitle;

    private String departureOrArrivalDate;

    private String departureOrArrivalTime;

    private Station stationFrom;

    private Station stationTo;

    private Date departureOrArrival;

    private boolean withoutTransfers;

    private String timeType;

    private boolean errorInputs = false;

    private List<FoundedPathsWrapper> foundResults;

    public void testNeo4j() {

        //stationNeo4jService.deleteAll();

        StationNode stationNode = new StationNode();
        stationNode.setTitle("zkouštičkaaaa");
        stationNode.setName("zkouštičkaaaaa jméno");
        StationNode stationNode1 = stationNeo4jService.create(stationNode);

        StationNode stationNode2 = new StationNode();
        stationNode2.setTitle("zk2aaaa");
        stationNode2.setName("name2aaaa");
        StationNode stationNode3 = stationNeo4jService.create(stationNode2);

        StationNode retrieved = stationNeo4jService.findById(10);
        StationNode retrieved2 = stationNeo4jService.findById(stationNode1.getId());

        Iterable<StationNode> all = stationNeo4jService.findAll();


        int i = 0;
    }

    public void performSearch() {
        long l = System.currentTimeMillis();
        prepareAndValidateInputs();
        testNeo4j();

        if(!errorInputs) {
            List<SearchResultWrapper> path;

            if(timeType.equals("departure")) {
                path = searchService.findPathByDepartureDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 12, withoutTransfers ? 0 : 2, -1);
            } else {
                path = searchService.findPathByArrivalDate(stationFrom.getId(), stationTo.getId(), departureOrArrival, 12, withoutTransfers ? 0 : 2, 3);
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

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setStopService(StopService stopService) {
        this.stopService = stopService;
    }

    public void setStationNeo4jService(StationNeo4jService stationNeo4jService) {
        this.stationNeo4jService = stationNeo4jService;
    }
}
