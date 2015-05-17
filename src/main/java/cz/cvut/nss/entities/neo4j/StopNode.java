package cz.cvut.nss.entities.neo4j;

import cz.cvut.nss.entities.neo4j.relationship.NextAwaitingStopRelationship;
import cz.cvut.nss.entities.neo4j.relationship.NextStopRelationship;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * StopNode in Neo4j entity.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
@NodeEntity
public class StopNode {

    public static final String STOP_PROPERTY = "stopId";

    public static final String STATION_PROPERTY = "stationId";

    public static final String RIDE_PROPERTY = "rideId";

    public static final String ARRIVAL_PROPERTY = "arrivalInMillis";

    public static final String DEPARTURE_PROPERTY = "departureInMillis";

    @GraphId
    private Long id;

    @GraphProperty
    private Long stopId;

    @Indexed
    @GraphProperty
    private Long stationId;

    @GraphProperty
    private Long rideId;

    @RelatedTo(type = "IN_RIDE", direction = Direction.OUTGOING)
    private RideNode rideNode;

    @Indexed
    @GraphProperty
    private Long arrivalInMillis;

    @Indexed
    @GraphProperty
    private Long departureInMillis;

    @RelatedToVia(type = "NEXT_STOP")
    private Set<NextStopRelationship> prevStopNodesRelationShips;

    @RelatedToVia(type = "NEXT_AWAITING_STOP")
    private Set<NextAwaitingStopRelationship> nextAwaitingStopRelationships;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStopId() {
        return stopId;
    }

    public void setStopId(Long stopId) {
        this.stopId = stopId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public RideNode getRideNode() {
        return rideNode;
    }

    public void setRideNode(RideNode rideNode) {
        this.rideNode = rideNode;
    }

    public Long getArrivalInMillis() {
        return arrivalInMillis;
    }

    public void setArrivalInMillis(Long arrivalInMillis) {
        this.arrivalInMillis = arrivalInMillis;
    }

    public Long getDepartureInMillis() {
        return departureInMillis;
    }

    public void setDepartureInMillis(Long departureInMillis) {
        this.departureInMillis = departureInMillis;
    }

    public void setPrevStopNodesRelationShips(Set<NextStopRelationship> prevStopNodesRelationShips) {
        this.prevStopNodesRelationShips = prevStopNodesRelationShips;
    }

    public Set<NextStopRelationship> getPrevStopNodesRelationShips() {
        if(prevStopNodesRelationShips == null) {
            prevStopNodesRelationShips = new HashSet<>();
        }

        return prevStopNodesRelationShips;
    }

    public void addPrevStopNodeRelationShip(NextStopRelationship prevStopNodeRelationShips) {
        if(!getPrevStopNodesRelationShips().contains(prevStopNodeRelationShips)) {
            getPrevStopNodesRelationShips().add(prevStopNodeRelationShips);
        }
    }

    public void setNextAwaitingStopRelationships(Set<NextAwaitingStopRelationship> nextAwaitingStopRelationships) {
        this.nextAwaitingStopRelationships = nextAwaitingStopRelationships;
    }

    public Set<NextAwaitingStopRelationship> getNextAwaitingStopRelationships() {
        if(nextAwaitingStopRelationships == null) {
            nextAwaitingStopRelationships = new HashSet<>();
        }

        return nextAwaitingStopRelationships;
    }

    public void addNextAwaitingStopRelationship(NextAwaitingStopRelationship nextAwaitingStopRelationship) {
        if(!getNextAwaitingStopRelationships().contains(nextAwaitingStopRelationship)) {
            getNextAwaitingStopRelationships().add(nextAwaitingStopRelationship);
        }
    }

    public void hasPrevStopNodeRelationShip(StopNode prevStop, Long travelTime) {
        addPrevStopNodeRelationShip(new NextStopRelationship(prevStop, this, travelTime));
    }

    public void hasNextAwaitingStopNodeRelationShip(StopNode nextAwaitingStop, Long waitingTime) {
        addNextAwaitingStopRelationship(new NextAwaitingStopRelationship(this, nextAwaitingStop, waitingTime));
    }

}
