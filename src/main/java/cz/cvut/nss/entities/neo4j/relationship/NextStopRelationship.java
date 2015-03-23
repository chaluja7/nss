package cz.cvut.nss.entities.neo4j.relationship;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.data.neo4j.annotation.*;

/**
 * @author jakubchalupa
 * @since 23.03.15
 */
@RelationshipEntity(type = "NEXT_STOP")
public class NextStopRelationship {

    @GraphId
    private Long id;

    @StartNode
    private StopNode stopFrom;

    @EndNode
    private StopNode stopTo;

    @GraphProperty
    private Long travelTime;

    public NextStopRelationship() {

    }

    public NextStopRelationship(StopNode stopFrom, StopNode stopTo, Long travelTime) {
        this.stopFrom = stopFrom;
        this.stopTo = stopTo;
        this.travelTime = travelTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StopNode getStopFrom() {
        return stopFrom;
    }

    public void setStopFrom(StopNode stopFrom) {
        this.stopFrom = stopFrom;
    }

    public StopNode getStopTo() {
        return stopTo;
    }

    public void setStopTo(StopNode stopTo) {
        this.stopTo = stopTo;
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
    }
}
