package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.entities.*;
import cz.cvut.nss.entities.enums.LineType;
import cz.cvut.nss.services.*;
import cz.cvut.nss.utils.EosDateTimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jakubchalupa
 * @since 15.03.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class StationNeo4jServiceTest {

    @Autowired
    private StopNeo4jService stopNeo4jService;

    @Autowired
    private StationService stationService;

    @Autowired
    private RideService rideService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private LineService lineService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteStopService routeStopService;

    @Autowired
    @Qualifier("neo4jSearchService")
    private SearchService searchService;

    @Autowired
    private GoogleStationService googleStationService;

    @Autowired
    private GoogleRouteService googleRouteService;

    @Autowired
    private GoogleServiceService googleServiceService;

    @Autowired
    private GoogleTripService googleTripService;

    @Autowired
    private GoogleStopService googleStopService;

    @Test
    @Ignore
    public void deleteAllStopNodesTest() {
        stopNeo4jService.deleteAll();
    }

    @Test
    @Ignore
    public void importAllStopNodesTest() {

        //do neo4j vrazim jednotlive RIDES
        List<Ride> rideList = rideService.getAll();
        for(Ride ride : rideList) {
            rideService.importRideToNeo4j(ride.getId());
        }

        //Nyni proiteruji vsechny stanice a propojim prislusne stopy na dane stanici cekacimi hranamy
        for(Station station : stationService.getAll()) {
            stopNeo4jService.connectStopNodesOnStationWithWaitingStopRelationship(station.getId());
        }
    }

    @Test
    public void testSearch() {


        List<SearchResultWrapper> pathsWithTransfers = searchService.findPathByDepartureDate(4, 7, new Date(1418061600000l), 5, 3, 5);

        int i = 0;

    }

    @Test
    public void importStations() throws IOException {
        //googleStationService.deleteAll();
//        importParentStations();
//        importChildStations();

        //googleRouteService.deleteAll();
        //importRoutes();

        //googleServiceService.deleteAll();
        //importServices();

        //googleTripService.deleteAll();
        //importTrips();

        //googleStopService.deleteAll();
        //importStops();

        //importLines();
    }

    public void importParentStations() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/FEL/semestr6/BP/google/20130625_20130625_stops.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

                String[] station = line.split(cvsSplitBy);
                if(station.length == 5) {
                    String stationId = station[0];
                    String stationName = station[1].replaceAll("\"", "");
                    double latitude = Double.parseDouble(station[2]);
                    double longitude = Double.parseDouble(station[3]);

                    String parentStationId = null;
                    Station stationByName = stationService.getStationByName(stationName);
                    if(stationByName != null) {
                        parentStationId = stationByName.getTitle();
                    } else {
                        Station stationJpa = new Station();
                        stationJpa.setTitle(stationId);
                        stationJpa.setName(stationName);
                        stationJpa.setLongtitude(longitude);
                        stationJpa.setLatitude(latitude);
                        stationJpa.setRegion(regionService.getRegion(1));
                        stationService.createStation(stationJpa);
                    }

                    googleStationService.createGoogleStation(stationId, stationName, latitude, longitude, parentStationId);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importChildStations() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/FEL/semestr6/BP/google/20130625_20130625_stops.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

                String[] station = line.split(cvsSplitBy);
                if(station.length == 6) {
                    String stationId = station[0];
                    String stationName = station[1].replaceAll("\"", "");
                    double latitude = Double.parseDouble(station[2]);
                    double longitude = Double.parseDouble(station[3]);

                    Station stationByName = stationService.getStationByName(stationName);
                    if(stationByName == null) {
                        throw new RuntimeException();
                    }

                    googleStationService.createGoogleStation(stationId, stationName, latitude, longitude, stationByName.getTitle());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importRoutes() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/FEL/semestr6/BP/google/20130625_20130625_routes.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

            String[] route = line.split(cvsSplitBy);
                String routeId = route[0];
                Integer agencyId = Integer.parseInt(route[1]);
                String name = route[2];
                Integer type = Integer.parseInt(route[4]);

                googleRouteService.createGoogleRoute(routeId, name, agencyId, type);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importServices() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/FEL/semestr6/BP/google/20130625_20130625_calendar.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        DateTimeFormatter formatter = DateTimeFormat.forPattern(EosDateTimeUtils.noDelimiterEnDatePattern);

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

                String[] service = line.split(cvsSplitBy);
                Integer serviceId = Integer.parseInt(service[0]);
                Boolean monday = false;
                if(service[1].equals("1")) {
                    monday = true;
                }
                Boolean tuesday = false;
                if(service[2].equals("1")) {
                    tuesday = true;
                }
                Boolean wednesday = false;
                if(service[3].equals("1")) {
                    wednesday = true;
                }
                Boolean thursday = false;
                if(service[4].equals("1")) {
                    thursday = true;
                }
                Boolean friday = false;
                if(service[5].equals("1")) {
                    friday = true;
                }
                Boolean saturday = false;
                if(service[6].equals("1")) {
                    saturday = true;
                }
                Boolean sunday = false;
                if(service[7].equals("1")) {
                    sunday = true;
                }

                LocalDateTime startDate = formatter.parseLocalDateTime(service[8]).plusYears(2);
                LocalDateTime endDate = formatter.parseLocalDateTime(service[9]).plusYears(2);

                googleServiceService.createGoogleService(serviceId, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startDate, endDate);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importTrips() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/FEL/semestr6/BP/google/20130625_20130625_trips.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 0) {
                    continue;
                }

                String[] trip = line.split(cvsSplitBy);
                String routeId = trip[0];
                Integer serviceId = Integer.parseInt(trip[1]);
                Integer tripId = Integer.parseInt(trip[2]);

                googleTripService.createGoogleTrip(tripId, routeId, serviceId);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importStops() throws IOException {

        String csvFile = "/Users/jakubchalupa/Documents/FEL/semestr6/BP/google/20130625_20130625_stop_times.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        DateTimeFormatter formatter = DateTimeFormat.forPattern(EosDateTimeUtils.timeWithSecondsPattern);


        try {

            int index = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if(index++ <= 2041045) {
                    continue;
                }

                String[] stop = line.split(cvsSplitBy);
                Integer tripId = Integer.parseInt(stop[0]);
                String stringArrival = stop[1];
                String stringDeparture = stop[2];

                if(tripId == 97848 || tripId == 97916) {
                    continue;
                }

                if(stringArrival.startsWith("24")) {
                    stringArrival = stringArrival.replaceFirst("24", "0");
                } else if(stringArrival.startsWith("25")) {
                    stringArrival = stringArrival.replaceFirst("25", "1");
                } else if(stringArrival.startsWith("31")) {
                    stringArrival = stringArrival.replaceFirst("31", "7");
                }

                if(stringDeparture.startsWith("24")) {
                    stringDeparture = stringDeparture.replaceFirst("24", "0");
                } else if(stringDeparture.startsWith("25")) {
                    stringDeparture = stringDeparture.replaceFirst("25", "1");
                } else if(stringDeparture.startsWith("31")) {
                    stringDeparture = stringDeparture.replaceFirst("31", "7");
                }

                //System.out.println(stop[1] + " - " + stringArrival + " -- " + stop[2] + " - " + stringDeparture);
                LocalDateTime arrivalTime = formatter.parseLocalDateTime(stringArrival);
                LocalDateTime departureTime = formatter.parseLocalDateTime(stringDeparture);
                String stationId = stop[3];
                Integer order = Integer.parseInt(stop[4]);

                googleStopService.createGoogleStop(stationId, tripId, order, arrivalTime, departureTime);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importLines() {
        for(GoogleRoute googleRoute : googleRouteService.findAll()) {
            //chci jen data dpp
            if(googleRoute.getAgencyId() != 1) {
                continue;
            }

            LineType lineType = null;
            switch(googleRoute.getType()) {
                case 0:
                    lineType = LineType.TRAM;
                    break;
                case 1:
                    lineType = LineType.METRO;
                    break;
                case 3:
                    lineType = LineType.BUS;
                    break;
                case 5:
                    lineType = LineType.BOAT;
                    break;
            }

            if(lineType == null) {
                throw new RuntimeException();
            }

            GoogleTrip googleTrip = googleTripService.getOneByRouteId(googleRoute.getRouteId());
            if(googleTrip == null) {
                throw new RuntimeException();
            }

            List<GoogleStop> googleStops = googleStopService.getGoogleStopsByTripId(googleTrip.getTripId());
            if(googleStops.size() < 2) {
                throw new RuntimeException();
            }

            List<Station> stationList = new ArrayList<>();
            for(GoogleStop googleStop : googleStops) {
                GoogleStation googleStation = googleStationService.getGoogleStationByStationId(googleStop.getStationId());
                if(googleStation.getParentStation() != null) {
                    googleStation = googleStation.getParentStation();
                }

                Station station = stationService.getStationByName(googleStation.getName());
                stationList.add(station);
            }

            Route route = new Route();
            route.setName(stationList.get(0).getName() + " - " + stationList.get(stationList.size() - 1).getName() + " (" + lineType.name() + " - " + googleRoute.getRouteId() + ")");
            routeService.createRoute(route);

            for(int i = 0; i < stationList.size(); i++) {
                RouteStop routeStop = new RouteStop();
                routeStop.setStation(stationList.get(i));
                routeStop.setDistance(i);
                routeStop.setRoute(route);

                routeStopService.createRouteStop(routeStop);
            }

            Line line = new Line();
            line.setLineType(lineType);
            line.setName(googleRoute.getName());
            line.setRoute(route);

            lineService.createLine(line);
        }
    }

}




