package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.services.SearchService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

/**
 * Search Neo4j service tests.
 *
 * @author jakubchalupa
 * @since 15.05.15
 */
public class SearchNeo4JServiceIT extends AbstractServiceNeo4jIT {

    @Autowired
    @Qualifier("neo4jSearchService")
    private SearchService searchService;

    @Autowired
    private StationService stationService;

    @Autowired
    private StopNeo4jService stopNeo4jService;

    private Random random;

    private List<Station> stationList;

    private LocalDateTime departureOrArrivalDate;

    /**
     * interval (hodin) pro vyhledavani
     */
    private final int SEARCH_INTERVAL_HOURS = 1;

    /**
     * pocet probehnuti testu hledani
     */
    private final int NUMBER_OF_TEST_ROUNDS = 1;

    /**
     * max pocet prestupu
     */
    private final int MAX_NUMBER_OF_TRANSFERS = 3;

    @Before
    public void setUp() {
        random = new Random();
        stationList = stationService.getAll();

        DateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.DATE_TIME_PATTERN);
        try {
            departureOrArrivalDate = new LocalDateTime(dateFormat.parse("13.07.2015 16:30"));
        } catch (ParseException e) {
            departureOrArrivalDate = new LocalDateTime();
        }
    }

    @Test
    @Ignore
    public void testFindPathsByDepartureDate() {
        for(int i = 0; i < NUMBER_OF_TEST_ROUNDS; i++) {
            int stationFromIndex = random.nextInt(stationList.size());
            int stationToIndex = random.nextInt(stationList.size());

            List<SearchResultWrapper> paths = searchService.findPathByDepartureDate(stationList.get(stationFromIndex).getId(), stationList.get(stationToIndex).getId(),
                    departureOrArrivalDate.toDate(), SEARCH_INTERVAL_HOURS, MAX_NUMBER_OF_TRANSFERS, -1);
            Assert.assertNotNull(paths);
            if (paths.size() > 0) {
                SearchResultWrapper prevWrapper = null;
                for (SearchResultWrapper wrapper : paths) {
                    Assert.assertNotNull(wrapper);
                    Assert.assertNotNull(wrapper.getStops());
                    Assert.assertTrue(wrapper.getStops().size() >= 2);
                    Assert.assertTrue(wrapper.getStops().size() <= (MAX_NUMBER_OF_TRANSFERS * 2 + 2));

                    Long departureStopId = wrapper.getStops().get(0);
                    Long arrivalStopId = wrapper.getStops().get(wrapper.getStops().size() - 1);
                    StopNode departureStopNode = stopNeo4jService.findByStopId(departureStopId);
                    StopNode arrivalStopNode = stopNeo4jService.findByStopId(arrivalStopId);

                    Assert.assertNotNull(departureStopNode);
                    Assert.assertNotNull(arrivalStopNode);
                    Assert.assertNotNull(departureStopNode.getStationId());
                    Assert.assertNotNull(arrivalStopNode.getStationId());
                    Assert.assertEquals(departureStopNode.getStationId(), stationList.get(stationFromIndex).getId());
                    Assert.assertEquals(arrivalStopNode.getStationId(), stationList.get(stationToIndex).getId());

                    if(prevWrapper != null) {
                        Assert.assertTrue(prevWrapper.getArrival() < wrapper.getArrival() ||
                                (wrapper.getArrival() < departureOrArrivalDate.getMillisOfDay() && prevWrapper.getArrival() >= departureOrArrivalDate.getMillisOfDay()));
                    }

                    prevWrapper = wrapper;
                }
            }
        }
    }

    @Test
    @Ignore
    public void testFindPathsByArrivalDate() {
        for(int i = 0; i < NUMBER_OF_TEST_ROUNDS; i++) {
            int stationFromIndex = random.nextInt(stationList.size());
            int stationToIndex = random.nextInt(stationList.size());

            List<SearchResultWrapper> paths = searchService.findPathByArrivalDate(stationList.get(stationFromIndex).getId(), stationList.get(stationToIndex).getId(),
                    departureOrArrivalDate.toDate(), SEARCH_INTERVAL_HOURS, MAX_NUMBER_OF_TRANSFERS, -1);
            Assert.assertNotNull(paths);
            if (paths.size() > 0) {
                SearchResultWrapper prevWrapper = null;
                for (SearchResultWrapper wrapper : paths) {
                    Assert.assertNotNull(wrapper);
                    Assert.assertNotNull(wrapper.getStops());
                    Assert.assertTrue(wrapper.getStops().size() >= 2);
                    Assert.assertTrue(wrapper.getStops().size() <= (MAX_NUMBER_OF_TRANSFERS * 2 + 2));

                    Long departureStopId = wrapper.getStops().get(0);
                    Long arrivalStopId = wrapper.getStops().get(wrapper.getStops().size() - 1);
                    StopNode departureStopNode = stopNeo4jService.findByStopId(departureStopId);
                    StopNode arrivalStopNode = stopNeo4jService.findByStopId(arrivalStopId);

                    Assert.assertNotNull(departureStopNode);
                    Assert.assertNotNull(arrivalStopNode);
                    Assert.assertNotNull(departureStopNode.getStationId());
                    Assert.assertNotNull(arrivalStopNode.getStationId());
                    Assert.assertEquals(departureStopNode.getStationId(), stationList.get(stationFromIndex).getId());
                    Assert.assertEquals(arrivalStopNode.getStationId(), stationList.get(stationToIndex).getId());

                    if(prevWrapper != null) {
                        Assert.assertTrue(prevWrapper.getArrival() < wrapper.getArrival() ||
                                (wrapper.getArrival() < departureOrArrivalDate.getMillisOfDay() && prevWrapper.getArrival() >= departureOrArrivalDate.getMillisOfDay()));
                    }

                    prevWrapper = wrapper;
                }
            }
        }
    }

}
