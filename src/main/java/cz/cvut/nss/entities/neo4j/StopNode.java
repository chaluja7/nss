package cz.cvut.nss.entities.neo4j;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

/**
 * @author jakubchalupa
 * @since 17.03.15
 */
@NodeEntity
public class StopNode {

    @GraphId
    private Long id;

    @GraphProperty
    private Long stopId;

    @Indexed
    @GraphProperty
    private Long stationId;

    @GraphProperty
    private Long rideId;

    @GraphProperty
    private Long arrivalInMillis;

    @GraphProperty
    private Long departureInMillis;

    @RelatedTo(type = "hasStop", direction = Direction.INCOMING)
    private StationNode stationNode;

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

    public StationNode getStationNode() {
        return stationNode;
    }

    public void setStationNode(StationNode stationNode) {
        this.stationNode = stationNode;
    }
}
