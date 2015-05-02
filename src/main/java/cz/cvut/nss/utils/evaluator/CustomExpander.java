package cz.cvut.nss.utils.evaluator;

import cz.cvut.nss.entities.neo4j.OperationIntervalNode;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import cz.cvut.nss.utils.CollectionCloneUtils;
import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.BranchState;
import org.neo4j.helpers.collection.Iterables;

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

    @Override
    public Iterable<Relationship> expand(Path path, BranchState<StationRideWrapper> stateBranchState) {

        //Predavani parametru PATH, pokazde musim vytvorit nove instance, aby se jednotlive path neovlivnovali
        StationRideWrapper stationRideWrapperOld = stateBranchState.getState();
        Map<Long, List<Long>> visitedStations = CollectionCloneUtils.cloneMap(stationRideWrapperOld.getVisitedStations());
        Set<Long> visitedRides = CollectionCloneUtils.cloneSet(stationRideWrapperOld.getVisitedRides());

        StationRideWrapper stationRideWrapper = new StationRideWrapper();
        stationRideWrapper.setVisitedStations(visitedStations);
        stationRideWrapper.setVisitedRides(visitedRides);
        stateBranchState.setState(stationRideWrapper);

        //inicializace parametru PATH
        Node startNode = path.startNode();
        Node currentNode = path.endNode();
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);
        long startNodeStationId = (long) startNode.getProperty(StopNode.STATION_PROPERTY);
        long currentNodeStopId = (long) currentNode.getProperty(StopNode.STOP_PROPERTY);
        long currentRideId = (long) currentNode.getProperty(StopNode.RIDE_PROPERTY);
        long currentStationId = (long) currentNode.getProperty(StopNode.STATION_PROPERTY);
        Relationship lastRelationShip = path.lastRelationship();

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
        //startNodeDepartureDateTime nyni obsahuje datum i cas vyjezdu currentNodu
        //startNodeMillisTime obsahuje jen cas vyjezdu (v millis od pulnoci) currentNodu

        //Jsem na prvnim NODu
        if(lastRelationShip == null) {
            List<Long> tmpVisitedRides = new ArrayList<>();
            tmpVisitedRides.add(currentRideId);

            visitedStations.put(currentStationId, tmpVisitedRides);
            visitedRides.add(currentRideId);
            visitedStops.put(currentNodeStopId, startNodeMillisTime);
            visitedRidesGlobal.put(currentRideId, startNodeMillisTime);

            //vratit chci z prvniho nodu jen node na NEXT_STOP relaci
            return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP);
        }

        if(currentStationId == startNodeStationId) {
            return Iterables.empty();
        }

        //musim zkonrolovat, zda aktualni node nema time jiz po case maxDepartureTime
        if (departureDayOfYear == maxDepartureDayOfYear) {
            //pohybuji se v ramci jednoho dne
            if (currentNodeTimeProperty < departureMillisOfDay || currentNodeTimeProperty >= maxDepartureMillisOfDay) {
                //presahl jsem casovy rozsah vyhledavani
                return Iterables.empty();
            }
        } else if (departureDayOfYear == maxDepartureDayOfYear - 1) {
            //prehoupl jsem se pres pulnoc
            if (currentNodeTimeProperty < departureMillisOfDay && currentNodeTimeProperty > maxDepartureMillisOfDay) {
                //presahl jsem casovy rozsah vyhledavani
                return Iterables.empty();
            }
        } else {
            //K tomu by nemelo dojit, neumime vyhledavat pres vice nez 24 hodin
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
                //interval platnosti RIDE musim zjistit, tedy musim pres hranu IN_RIDE k ride a odtud pres hranu IN_INTERVAL k intervalu platnosti
                Relationship toRideRelationShip = currentNode.getSingleRelationship(RelTypes.IN_RIDE, Direction.OUTGOING);
                Node rideNode = toRideRelationShip.getEndNode();

                Relationship toOperationIntervalRelationship = rideNode.getSingleRelationship(RelTypes.IN_INTERVAL, Direction.OUTGOING);
                Node operationIntervalNode = toOperationIntervalRelationship.getEndNode();

                operationIntervalNodeFromDateInMillis = (Long) operationIntervalNode.getProperty(OperationIntervalNode.FROM_DATE_PROPERTY);
                //k datu do prictu jeden den, protoze platnost plati do posledniho dne VCETNE
                operationIntervalNodeToDateInMillis = (Long) operationIntervalNode.getProperty(OperationIntervalNode.TO_DATE_PROPERTY) + DateTimeUtils.MILLIS_IN_DAY;

                //musim doiterovat na zacatek ridy a zjistit kdy vyjizdela, interval platnosti se totiz vztahuje k dobe vyjezdu z prvniho stopu
                Node firstNodeOnRide = currentNode;
                while (firstNodeOnRide.hasRelationship(RelTypes.NEXT_STOP, Direction.INCOMING)) {
                    firstNodeOnRide = firstNodeOnRide.getSingleRelationship(RelTypes.NEXT_STOP, Direction.INCOMING).getStartNode();
                }

                //cas vyjezdu teto ridy (tripu)
                firstNodeOnRideDeparture = (Long) firstNodeOnRide.getProperty(StopNode.DEPARTURE_PROPERTY);

                //platnosti pro jednotlive dny v tydnu
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
                //neprehoupl jsem se s ridou pres pulnoc (rida vyjela pred pulnoci a momentalne jsem porad pred pulnoci)
                if (currentNodeTimeProperty >= departureMillisOfDay) {
                    //neprehoupl jsem se pres pulnoc (pohybuji se v ramci dne, ve kterem jsem hledal odjezd)
                    //zjistuji datum pro departureDateTime
                    if (operationIntervalNodeFromDateInMillis > departureDateTime.toDate().getTime() ||
                            operationIntervalNodeToDateInMillis < departureDateTime.toDate().getTime() ||
                            !operationDayForRideMap.get(departureDateTime.getDayOfWeek())) {
                        //tato rida neni v platnosti, nesmim tedy pokracovat relaci next_stop
                        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP);
                    }
                } else {
                    //zjistuji datum pro maxDepartureDateTime (pohybuji se v ramci dne nasledujiciho po dni, od kdy hledam odjezd)
                    if (operationIntervalNodeFromDateInMillis > maxDepartureDateTime.toDate().getTime() ||
                            operationIntervalNodeToDateInMillis < maxDepartureDateTime.toDate().getTime() ||
                            !operationDayForRideMap.get(maxDepartureDateTime.getDayOfWeek())) {
                        //tato rida neni v platnosti, nesmim tedy pokracovat relaci next_stop
                        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_AWAITING_STOP);
                    }
                }
            } else {
                //prehoupl jsem se s ridou pres pulnoc (rida vyjizdela pred pulnoci, ted uz je po pulnoci)
                if (currentNodeTimeProperty >= departureMillisOfDay) {
                    //neprehoupl jsem se pres pulnoc (pohybuji se v ramci dne, ve kterem jsem hledal odjezd)
                    //zjistuji datum pro departureDateTime - 1 den protoze rida vyjela vcera
                    if (operationIntervalNodeFromDateInMillis > (departureDateTime.toDate().getTime() - DateTimeUtils.MILLIS_IN_DAY) ||
                            operationIntervalNodeToDateInMillis < (departureDateTime.toDate().getTime() - DateTimeUtils.MILLIS_IN_DAY) ||
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
            //posledni hrana byla next_stop, mimo jine to znamena, ze currentNodeArrival nemuze byt null
            assert(currentNodeArrival != null);

            //v ramci teto path jsem na teto stanici jiz byl (tedy se vracim, coz je nezadouci)
            //ovsem v ramci jedne ridy muzu na jednu stanici vicenasobne
            if(visitedStations.containsKey(currentStationId)) {
                List<Long> ridesWithThisStation = visitedStations.get(currentStationId);
                for(Long r : ridesWithThisStation) {
                    if(r != currentRideId) {
                        return Iterables.empty();
                    }
                }
            } else {
                visitedStations.put(currentStationId, new ArrayList<Long>());
            }

            //kontrola casu na teto stanici v ramci path od zacatku
            //osetreni pres pulnoc
            LocalDateTime currentStopArrivalDateTime = new LocalDateTime(departureDateTime);
            if(departureMillisOfDay > currentNodeArrival) {
                //prehoupl jsem se pres pulnoc
                currentStopArrivalDateTime = currentStopArrivalDateTime.plusDays(1);
            }

            currentStopArrivalDateTime = currentStopArrivalDateTime.millisOfDay().withMinimumValue();
            currentStopArrivalDateTime = currentStopArrivalDateTime.plusMillis(currentNodeArrival.intValue());
            long currentNodeMillis = currentStopArrivalDateTime.toDateTime().getMillis();
            //v currentStopArrivalDateTime je datum a cas aktualniho nodu
            //v currentNodeMillis je jen cas aktualniho nodu

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
                    //porovnání, pozor na pulnoc :)
                    Long currentStationOnPathPrevBestArrival = currentVisitedStationsOnPathMap.get(currentStationId);
                    if(currentStationOnPathPrevBestArrival >= departureMillisOfDay) {
                        //predchozi nejlepsi byl pred pulnoci
                        if(currentStationOnPathPrevBestArrival < currentNodeMillis || currentNodeMillis < departureMillisOfDay) {
                            //predtim byl jiz lepsi cas a nebo ten aktualni je az po pulnoci (Tedy ten predtim je lepsi v kazdem pripade
                            if(!visitedStations.get(currentStationId).contains(currentRideId)) {
                                //ukoncim to jen pokud jsem na teto stanici nebyl pri aktualni ride, to jsem tam totiz mohl byt vicekrat
                                return Iterables.empty();
                            }
                        } else {
                            //ulozim aktualni nejlepsi cas na teto stanici v ramci path
                            currentVisitedStationsOnPathMap.put(currentStationId, currentNodeMillis);
                        }
                    } else {
                        //predchozi nejlepsi byl po pulnoci
                        if(currentNodeMillis < departureMillisOfDay && currentNodeMillis > currentStationOnPathPrevBestArrival) {
                            //ted jsem taky po pulnoci ale pozdeji, nez byl predchozi nejlepsi
                            if(!visitedStations.get(currentStationId).contains(currentRideId)) {
                                //ukoncim to jen pokud jsem na teto stanici nebyl pri aktualni ride, to jsem tam totiz mohl byt vicekrat
                                return Iterables.empty();
                            }
                        } else {
                            //ulozim aktualni nejlepsi cas na teto stanici v ramci path
                            currentVisitedStationsOnPathMap.put(currentStationId, currentNodeMillis);
                        }
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
                        Long prevBestTimeOnStop = visitedStops.get(currentNodeStopId);
                        if(prevBestTimeOnStop >= departureMillisOfDay) {
                            //predchozi nejlepsi cas byl pred pulnoci
                            if(startNodeMillisTime < prevBestTimeOnStop && startNodeMillisTime >= departureMillisOfDay) {
                                //momentalne jsem taky pred pulnoci ale s drivejsim vyjezdem, to nechci :)
                                return Iterables.empty();
                            }
                        } else {
                            //prechozi nejlepsi cas byl po pulnoci
                            if((startNodeMillisTime < prevBestTimeOnStop && startNodeMillisTime < departureMillisOfDay) || startNodeMillisTime >= departureMillisOfDay) {
                                //momentalne jsem taky po pulnoci ale s drivejsim vyjezdem, nebo jsem dokonce pred pulnoci, to nechci :)
                                return Iterables.empty();
                            }
                        }
                    }

                    //pokud to prislo sem, tak mam aktualne nejlepsi mozny cas vyjezdu k tomuto stopu
                    visitedStops.put(currentNodeStopId, startNodeMillisTime);

                    //kontrola unikatnosti ridy v ramci path
                    if (visitedRides.contains(currentRideId)) {
                        return Iterables.empty();
                    } else {
                        if (visitedRidesGlobal.containsKey(currentRideId)) {
                            Long prevBestTimeOnRide = visitedRidesGlobal.get(currentRideId);
                            if(prevBestTimeOnRide >= departureMillisOfDay) {
                                //predchozi nejlepsi cas byl pred pulnoci
                                if(startNodeMillisTime < prevBestTimeOnRide && startNodeMillisTime >= departureMillisOfDay) {
                                    //momentalne jsem taky pred pulnoci ale s drivejsim vyjezdem, to nechci :)
                                    return Iterables.empty();
                                }
                            } else {
                                //prechozi nejlepsi cas byl po pulnoci
                                if((startNodeMillisTime < prevBestTimeOnRide && startNodeMillisTime < departureMillisOfDay) || startNodeMillisTime >= departureMillisOfDay) {
                                    //momentalne jsem taky po pulnoci ale s drivejsim vyjezdem, nebo jsem po pulnoci to nechci
                                    return Iterables.empty();
                                }
                            }
                        }

                        //pokud to prislo az sem, tak mam aktualne nejlepsi mozny cas vyjezdu k teto ride
                        visitedRidesGlobal.put(currentRideId, startNodeMillisTime);
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
                    //iterovat chci jen maximalne pres 2 relace dozadu, vice nema smysl
                    break;
                }
            }
        }

        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP, RelTypes.NEXT_AWAITING_STOP);
    }

    @Override
    public PathExpander<StationRideWrapper> reverse() {
        return this;
    }

}
