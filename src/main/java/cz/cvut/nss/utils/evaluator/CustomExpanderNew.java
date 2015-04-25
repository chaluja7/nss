package cz.cvut.nss.utils.evaluator;

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
public class CustomExpanderNew implements PathExpander<StationRideWrapper> {

    private final LocalDateTime departureDateTime;

    private final LocalDateTime maxDepartureDateTime;

    private final int maxNumberOfTransfers;

    private Map<Long, Set<Long>> visitedStationsFromStartNodeGlobalMap = new HashMap<>();

    public CustomExpanderNew(LocalDateTime departureDateTime, LocalDateTime maxDepartureDateTime, int maxNumberOfTransfers) {
        this.departureDateTime = departureDateTime;
        this.maxDepartureDateTime = maxDepartureDateTime;
        this.maxNumberOfTransfers = maxNumberOfTransfers;

    }

    public static Set<Long> cloneSet(Set<Long> set) {
        Set<Long> clone = new HashSet<>(set.size());
        for(Long item : set) {
            clone.add(new Long(item));
        }
        return clone;
    }

    @Override
    public Iterable<Relationship> expand(Path path, BranchState<StationRideWrapper> stateBranchState) {

        Node startNode = path.startNode();
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);

        Node currentNode = path.endNode();
        long currentNodeStationId = (long) currentNode.getProperty(StopNode.STATION_PROPERTY);
        long currentNodeRideId = (long) currentNode.getProperty(StopNode.RIDE_PROPERTY);

        Relationship lastRelationship = path.lastRelationship();


        StationRideWrapper currentStateOld = stateBranchState.getState();
        Set<Long> visitedRidesOld = currentStateOld.getVisitedRides();
        Set<Long> visitedStationsOld = currentStateOld.getVisitedStations();

        Set<Long> visitedRidesNew = cloneSet(visitedRidesOld);
        Set<Long> visitedStationsNew = cloneSet(visitedStationsOld);

        StationRideWrapper currentStateNew = new StationRideWrapper();
        currentStateNew.setVisitedRides(visitedRidesNew);
        currentStateNew.setVisitedStations(visitedStationsNew);
        stateBranchState.setState(currentStateNew);

        //jsem na prvnim nodu
        if(lastRelationship == null) {
            visitedRidesNew.add(currentNodeRideId);
            visitedStationsNew.add(currentNodeStationId);
            return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP);
        }

        //zjistim departure tohoto nodu, pokud neexistuje tak vezmu arrival
        Long currentNodeTimeProperty = null;
        if(currentNode.hasProperty(StopNode.DEPARTURE_PROPERTY)) {
            currentNodeTimeProperty = (Long) currentNode.getProperty(StopNode.DEPARTURE_PROPERTY);
        } else {
            currentNodeTimeProperty = (Long) currentNode.getProperty(StopNode.ARRIVAL_PROPERTY);
        }

        if(currentNodeTimeProperty == null) {
            throw new RuntimeException();
        }


        //musim zkonrolovat, zda aktualni node nema time jiz po case maxDepartureTime
        if (departureDateTime.getDayOfYear() == maxDepartureDateTime.getDayOfYear()) {
            //pohybuji se v ramci jednoho dne
            if (currentNodeTimeProperty < departureDateTime.getMillisOfDay() || currentNodeTimeProperty >= maxDepartureDateTime.getMillisOfDay()) {
                //presahl jsem casovy rozsah vyhledavani
                return Collections.EMPTY_SET;
            }
        } else if (departureDateTime.getDayOfYear() == maxDepartureDateTime.getDayOfYear() - 1) {
            //prehoupl jsem se pres pulnoc
            if (currentNodeTimeProperty >= departureDateTime.getMillisOfDay() || currentNodeTimeProperty < maxDepartureDateTime.getMillisOfDay()) {
                //OK
            } else {
                //presahl jsem casovy rozsah vyhledavani
                return Collections.EMPTY_SET;
            }
        } else {
            //todo co preklenuti roku?
            throw new RuntimeException();
        }



        if(lastRelationship.isType(RelTypes.NEXT_AWAITING_STOP)) {
            //prisel jsem na tento node prestupem











        } else {
            //prisel jsem na tento node jako next_stop

            //zda jsem v ramci teto path (od zacatku) jiz byl na teto stanici
            if(!visitedStationsFromStartNodeGlobalMap.containsKey(startNodeStopId)) {
                Set<Long> visitedStationsFromThisStartNode = new HashSet<>();
                visitedStationsFromThisStartNode.add(currentNodeStationId);
                visitedStationsFromStartNodeGlobalMap.put(startNodeStopId, visitedStationsFromThisStartNode);
            } else {
                //jiz mam mapu
                Set<Long> visitedStationsFromThisStartNode = visitedStationsFromStartNodeGlobalMap.get(startNodeStopId);
                if(visitedStationsFromThisStartNode.contains(currentNodeStationId)) {
                    return Collections.EMPTY_SET;
                } else {
                    visitedStationsFromThisStartNode.add(currentNodeStationId);
                }
            }


            //max pocet prestupu
            if(!visitedRidesNew.contains(currentNodeRideId)) {
                visitedRidesNew.add(currentNodeRideId);
            }

            if(visitedRidesNew.size() > maxNumberOfTransfers) {
                return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP);
            }




        }







        return currentNode.getRelationships(Direction.OUTGOING, RelTypes.NEXT_STOP, RelTypes.NEXT_AWAITING_STOP);
    }

    @Override
    public PathExpander<StationRideWrapper> reverse() {
        return this;
    }

}
