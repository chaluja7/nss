package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import org.joda.time.LocalDateTime;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.BranchState;

import java.util.*;

/**
 * @author jakubchalupa
 * @since 19.04.15
 */
public class CustomExpander implements PathExpander<StationRideWrapper> {

    private final LocalDateTime departureDateTime;

    private final LocalDateTime maxDepartureDateTime;

    private final int maxNumberOfTransfers;

    private final int departureDayOfYear;

    private final int maxDepartureDayOfYear;

    private final int departureMillisOfDay;

    private final int maxDepartureMillisOfDay;

    private final Map<Long, Long> visitedStops = new HashMap<>();

    private final Map<Long, Long> operationIntervalForRidesFromDate = new HashMap<>();

    private final Map<Long, Long> operationIntervalForRideToDate = new HashMap<>();

    private final Map<Long, Map<Integer, Boolean>> operationDaysForRide = new HashMap<>();

    private final Map<Long, Long> firstNodeOnRideDepartureMap = new HashMap<>();

    private final Map<Long, Long> visitedRidesGlobal = new HashMap<>();

    private final Map<Long, Map<Long, Long>> visitedStationsOnPathMap = new HashMap<>();

    public CustomExpander(LocalDateTime departureDateTime, LocalDateTime maxDepartureDateTime, int maxNumberOfTransfers) {
        this.departureDateTime = departureDateTime;
        this.maxDepartureDateTime = maxDepartureDateTime;
        this.maxNumberOfTransfers = maxNumberOfTransfers;

        this.departureDayOfYear = departureDateTime.getDayOfYear();
        this.maxDepartureDayOfYear = maxDepartureDateTime.getDayOfYear();
        this.departureMillisOfDay = departureDateTime.getMillisOfDay();
        this.maxDepartureMillisOfDay = maxDepartureDateTime.getMillisOfDay();
    }

    public static Set<Long> cloneSet(Set<Long> set) {
        Set<Long> clone = new HashSet<>(set.size());
        for(Long item : set) {
            clone.add(new Long(item));
        }
        return clone;
    }

    public static List<Long> cloneList(List<Long> list) {
        List<Long> clone = new ArrayList<>(list.size());
        for(Long item : list) {
            clone.add(new Long(item));
        }
        return clone;
    }

    public static Map<Long, List<Long>> cloneMap(Map<Long, List<Long>> map) {
        Map<Long, List<Long>> clone = new HashMap<>(map.size());
        for (Map.Entry<Long, List<Long>> entry : map.entrySet()) {
            List<Long> cloneList = new ArrayList<>();
            for(Long item : entry.getValue()) {
                cloneList.add(new Long(item));
            }

            clone.put(new Long(entry.getKey()), cloneList);
        }

        return clone;
    }

    @Override
    public Iterable<Relationship> expand(Path path, BranchState<StationRideWrapper> stateBranchState) {

        //Predavani parametru PATH
        StationRideWrapper stationRideWrapperOld = stateBranchState.getState();
        Map<Long, List<Long>> visitedStations = cloneMap(stationRideWrapperOld.getVisitedStations());
        Set<Long> visitedRides = cloneSet(stationRideWrapperOld.getVisitedRides());
        StationRideWrapper stationRideWrapper = new StationRideWrapper();
        stationRideWrapper.setVisitedStations(visitedStations);
        stationRideWrapper.setVisitedRides(visitedRides);
        stateBranchState.setState(stationRideWrapper);

        //inicializace parametru PATH
        Node startNode = path.startNode();
        Node currentNode = path.endNode();
        long startNodeStationId = (long) startNode.getProperty(StopNode.STATION_PROPERTY);
        long currentNodeStopId = (long) currentNode.getProperty(StopNode.STOP_PROPERTY);
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);
        Relationship lastRelationShip = path.lastRelationship();
        long currentRideId = (long) currentNode.getProperty(StopNode.RIDE_PROPERTY);
        long currentStationId = (long) currentNode.getProperty(StopNode.STATION_PROPERTY);


        //rozhodujici je departureTime pokud existuje, jinak arrival time
        Long currentNodeArrival = null;
        Long currentNodeDeparture = null;
        if (currentNode.hasProperty(StopNode.DEPARTURE_PROPERTY)) {
            currentNodeDeparture = (Long) currentNode.getProperty(StopNode.DEPARTURE_PROPERTY);
        }
        if (currentNode.hasProperty(StopNode.ARRIVAL_PROPERTY)) {
            currentNodeArrival = (Long) currentNode.getProperty(StopNode.ARRIVAL_PROPERTY);
        }

        if (currentNodeArrival == null && currentNodeDeparture == null) {
            throw new RuntimeException();
        }

        Long currentNodeTimeProperty = currentNodeDeparture != null ? currentNodeDeparture : currentNodeArrival;


        //osetreni pres pulnoc
        LocalDateTime startNodeDepartureDateTime = new LocalDateTime(departureDateTime);
        if(departureMillisOfDay > currentNodeTimeProperty) {
            //prehoupl jsem se pres pulnoc
            startNodeDepartureDateTime = startNodeDepartureDateTime.plusDays(1);
        }

        startNodeDepartureDateTime = startNodeDepartureDateTime.millisOfDay().withMinimumValue();
        startNodeDepartureDateTime = startNodeDepartureDateTime.plusMillis(currentNodeTimeProperty.intValue());
        long startNodeMillisTime = startNodeDepartureDateTime.toDateTime().getMillis();

        //Jsem na prvnim NODu
        if(lastRelationShip == null) {
            List<Long> tmpVisitedRides = new ArrayList<>();
            tmpVisitedRides.add(currentRideId);
            visitedStations.put(currentStationId, tmpVisitedRides);
            visitedRides.add(currentRideId);
            visitedStops.put(currentNodeStopId, startNodeMillisTime);
            visitedRidesGlobal.put(currentRideId, startNodeMillisTime);
            return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP);
        }


        if(currentStationId == startNodeStationId) {
            return Collections.EMPTY_SET;
        }

        //musim zkonrolovat, zda aktualni node nema time jiz po case maxDepartureTime
        if (departureDayOfYear == maxDepartureDayOfYear) {
            //pohybuji se v ramci jednoho dne
            if (currentNodeTimeProperty < departureMillisOfDay || currentNodeTimeProperty >= maxDepartureMillisOfDay) {
                //presahl jsem casovy rozsah vyhledavani
                return Collections.EMPTY_SET;
            }
        } else if (departureDayOfYear == maxDepartureDayOfYear - 1) {
            //prehoupl jsem se pres pulnoc
            if (currentNodeTimeProperty >= departureMillisOfDay || currentNodeTimeProperty <= maxDepartureMillisOfDay) {
                //OK
            } else {
                //presahl jsem casovy rozsah vyhledavani
                return Collections.EMPTY_SET;
            }
        } else {
            throw new RuntimeException();
        }

        //Posledni hrana byla cekaci
        if(lastRelationShip.isType(RelTypes.NEXT_AWAITING_STOP)) {
            //a jeste zkontroluji, zda je vubec rida v platnosti, pokud neni musim pokracovat pouze relaci next_awaiting_stop
            long operationIntervalNodeFromDateInMillis;
            long operationIntervalNodeToDateInMillis;
            long firstNodeOnRideDeparture;
            Map<Integer, Boolean> operationDayForRideMap;

            //jiz mam v pameti interval platnosti teto RIDE
            if (firstNodeOnRideDepartureMap.containsKey(currentRideId)) {
                operationIntervalNodeFromDateInMillis = operationIntervalForRidesFromDate.get(currentRideId);
                operationIntervalNodeToDateInMillis = operationIntervalForRideToDate.get(currentRideId);
                firstNodeOnRideDeparture = firstNodeOnRideDepartureMap.get(currentRideId);
                operationDayForRideMap = operationDaysForRide.get(currentRideId);
            } else {
                //interval platnosti RIDE musim zjistit
                Relationship toRideRelationShip = currentNode.getSingleRelationship(RelTypes.IN_RIDE, Direction.OUTGOING);
                Node rideNode = toRideRelationShip.getEndNode();

                Relationship toOperationIntervalRelationship = rideNode.getSingleRelationship(RelTypes.IN_INTERVAL, Direction.OUTGOING);
                Node operationIntervalNode = toOperationIntervalRelationship.getEndNode();

                operationIntervalNodeFromDateInMillis = (Long) operationIntervalNode.getProperty(OperationIntervalNode.FROM_DATE_PROPERTY);
                operationIntervalNodeToDateInMillis = (Long) operationIntervalNode.getProperty(OperationIntervalNode.TO_DATE_PROPERTY);

                //musim doiterovat na zacatek ridy a zjistit kdy vyjizdela
                Node firstNodeOnRide = currentNode;
                while (firstNodeOnRide.hasRelationship(RelTypes.NEXT_STOP, Direction.INCOMING)) {
                    firstNodeOnRide = firstNodeOnRide.getSingleRelationship(RelTypes.NEXT_STOP, Direction.INCOMING).getStartNode();
                }

                firstNodeOnRideDeparture = (Long) firstNodeOnRide.getProperty(StopNode.DEPARTURE_PROPERTY);

                operationDayForRideMap = new HashMap<>();
                operationDayForRideMap.put(1, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.MONDAY));
                operationDayForRideMap.put(2, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.TUESDAY));
                operationDayForRideMap.put(3, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.WEDNESDAY));
                operationDayForRideMap.put(4, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.THURSDAY));
                operationDayForRideMap.put(5, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.FRIDAY));
                operationDayForRideMap.put(6, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.SATURDAY));
                operationDayForRideMap.put(7, (Boolean) operationIntervalNode.getProperty(OperationIntervalNode.SUNDAY));

                operationDaysForRide.put(currentRideId, operationDayForRideMap);
                firstNodeOnRideDepartureMap.put(currentRideId, firstNodeOnRideDeparture);
                operationIntervalForRidesFromDate.put(currentRideId, operationIntervalNodeFromDateInMillis);
                operationIntervalForRideToDate.put(currentRideId, operationIntervalNodeToDateInMillis);
            }


            if (firstNodeOnRideDeparture < currentNodeTimeProperty) {
                //neprehoupl jsem se s ridou pres pulnoc
                if (currentNodeTimeProperty > departureMillisOfDay) {
                    //neprehoupl jsem se pres pulnoc
                    //zjistuji datum pro depratureDateTime
                    if (operationIntervalNodeFromDateInMillis > departureDateTime.toDate().getTime() ||
                            operationIntervalNodeToDateInMillis < departureDateTime.toDate().getTime() ||
                            !operationDayForRideMap.get(departureDateTime.getDayOfWeek())) {
                        //tato rida neni v platnosti, nesmim tedy pokracovat relaci next_stop
                        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP);
                    }
                } else {
                    //zjistuji datum pro maxDepartureDateTime
                    if (operationIntervalNodeFromDateInMillis > maxDepartureDateTime.toDate().getTime() ||
                            operationIntervalNodeToDateInMillis < maxDepartureDateTime.toDate().getTime() ||
                            !operationDayForRideMap.get(maxDepartureDateTime.getDayOfWeek())) {
                        //tato rida neni v platnosti, nesmim tedy pokracovat relaci next_stop
                        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP);
                    }
                }
            } else {
                //prehoupl jsem se s ridou pres pulnoc
                if (currentNodeTimeProperty > departureMillisOfDay) {
                    //neprehoupl jsem se pres pulnoc
                    //zjistuji datum pro departureDateTime - 1 den
                    if (operationIntervalNodeFromDateInMillis > (departureDateTime.toDate().getTime() - 86400000) ||
                            operationIntervalNodeToDateInMillis < (departureDateTime.toDate().getTime() - 86400000) ||
                            !operationDayForRideMap.get(new LocalDateTime(departureDateTime).minusDays(1).getDayOfWeek())) {
                        //tato rida neni v platnosti, nesmim tedy pokracovat relaci next_stop
                        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP);
                    }
                } else {
                    //prehoupl jsem se pres pulnoc
                    //zjistuji datum pro departureDateTime
                    if (operationIntervalNodeFromDateInMillis > departureDateTime.toDate().getTime() ||
                            operationIntervalNodeToDateInMillis < departureDateTime.toDate().getTime() ||
                            !operationDayForRideMap.get(departureDateTime.getDayOfWeek())) {
                        //tato rida neni v platnosti, nesmim tedy pokracovat relaci next_stop
                        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP);
                    }
                }

            }


        } else {
            //posledni hrana byla next_stop

            //v ramci teto path jsem na teto stanici jiz byl (tedy se vracim, coz je nezadouci)
            if(visitedStations.containsKey(currentStationId)) {
                List<Long> ridesWithThisStation = visitedStations.get(currentStationId);
                for(Long r : ridesWithThisStation) {
                    if(r != currentRideId) {
                        return Collections.EMPTY_SET;
                    }
                }
            } else {
                visitedStations.put(currentStationId, new ArrayList<Long>());
            }

            //kontrola casu na teto stanici v ramci path od zacatku



            //osetreni pres pulnoc
            LocalDateTime currentStopDepartureDateTime = new LocalDateTime(departureDateTime);
            if(departureMillisOfDay > currentNodeArrival) {
                //prehoupl jsem se pres pulnoc
                currentStopDepartureDateTime = currentStopDepartureDateTime.plusDays(1);
            }

            currentStopDepartureDateTime = currentStopDepartureDateTime.millisOfDay().withMinimumValue();
            currentStopDepartureDateTime = currentStopDepartureDateTime.plusMillis(currentNodeArrival.intValue());
            long currentNodeMillis = currentStopDepartureDateTime.toDateTime().getMillis();


            if(!visitedStationsOnPathMap.containsKey(startNodeStopId)) {
                //jeste vubec neni vytvorena mapa navstivenych stanic pro tuto PATH
                Map<Long, Long> stationsAndArrivalMap = new HashMap<>();
                stationsAndArrivalMap.put(currentStationId, currentNodeMillis);
                visitedStationsOnPathMap.put(startNodeStopId, stationsAndArrivalMap);
            } else {
                //mapa navstivenych stanic pro tuto path je jiz vytvorena
                Map<Long, Long> currentVisitedStationsOnPathMap = visitedStationsOnPathMap.get(startNodeStopId);
                if(!currentVisitedStationsOnPathMap.containsKey(currentStationId)) {
                    //na teto stanici jsem v ramci PATH jeste nebyl
                    currentVisitedStationsOnPathMap.put(currentStationId, currentNodeMillis);
                } else {

                    //porovnání
                    Long currentStationOnPathPrevBestArrival = currentVisitedStationsOnPathMap.get(currentStationId);

                    //TODO tohle nefunguje, melo by se to smazat
                    if(currentStationOnPathPrevBestArrival < currentNodeMillis && !visitedStations.get(currentStationId).contains(currentRideId)) {
                        return Collections.EMPTY_SET;
                    } else if(!visitedStations.get(currentStationId).contains(currentRideId) && currentStationOnPathPrevBestArrival < currentNodeMillis) {
                        currentVisitedStationsOnPathMap.put(currentStationId, currentNodeMillis);
                    }

                }
            }

            visitedStations.get(currentStationId).add(currentRideId);

            //max pocet prestupu
            if(visitedRides.size() > maxNumberOfTransfers) {
                return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP);
            }

            int i = 0;
            RelTypes prevRelationShipType = null;
            for(Relationship relationship : path.reverseRelationships()) {
                boolean relationshipIsTypeNextAwaitingStop = relationship.isType(RelTypes.NEXT_AWAITING_STOP);

                //sel jsem (N)-[NEXT_AWAITING_STOP]-(m)-[NEXT_STOP]-(o)
                if(prevRelationShipType != null && relationshipIsTypeNextAwaitingStop && prevRelationShipType.equals(RelTypes.NEXT_STOP)) {

                    //na tomto stopu jsem jiz drive byl
                    if(visitedStops.containsKey(currentNodeStopId)) {
                        //a byl jsem na nem v pro me s priznivejsim casem vyjezdu
                        if(visitedStops.get(currentNodeStopId) > startNodeMillisTime) {
                            return Collections.EMPTY_SET;
                        }
                    }

                    visitedStops.put(currentNodeStopId, startNodeMillisTime);

                    //kontrola unikatnosti ridy v ramci path
                    if (visitedRides.contains(currentRideId)) {
                        return Collections.EMPTY_SET;
                    } else {
                        if (visitedRidesGlobal.containsKey(currentRideId)) {
                            if (visitedRidesGlobal.get(currentRideId) > startNodeMillisTime) {
                                return Collections.EMPTY_SET;
                            }
                        } else {
                            visitedRidesGlobal.put(currentRideId, startNodeMillisTime);
                        }

                        visitedRides.add(currentRideId);
                        if(visitedRides.size() > maxNumberOfTransfers) {
                            return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP);
                        }
                    }

                }

                if(relationshipIsTypeNextAwaitingStop) {
                    prevRelationShipType = RelTypes.NEXT_AWAITING_STOP;
                } else {
                    prevRelationShipType = RelTypes.NEXT_STOP;
                }

                if(++i >= 2) {
                    break;
                }
            }

        }




//        LinkedList<Relationship> queue = new LinkedList<>();
//
//        if(currentNode.hasRelationship(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP)) {
//            queue.add(currentNode.getSingleRelationship(RelTypes.NEXT_AWAITING_STOP, Direction.OUTGOING));
//        }
//
//        if(currentNode.hasRelationship(Direction.OUTGOING, RelTypes.NEXT_STOP)) {
//            queue.addFirst(currentNode.getSingleRelationship(RelTypes.NEXT_STOP, Direction.OUTGOING));
//        }
//
//
//
//
//        return queue;


        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP, RelTypes.NEXT_AWAITING_STOP);
    }

    @Override
    public PathExpander<StationRideWrapper> reverse() {
        return this;
    }

}
