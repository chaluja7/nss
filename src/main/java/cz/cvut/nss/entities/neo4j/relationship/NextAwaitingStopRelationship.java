package cz.cvut.nss.entities.neo4j.relationship;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.data.neo4j.annotation.*;

/**
 * Relace, slouzi pro urceni dalsich moznych spoju pro prestup.
 *
 * @author jakubchalupa
 * @since 23.03.15
 */
@RelationshipEntity(type = "NEXT_AWAITING_STOP")
public class NextAwaitingStopRelationship {

    @GraphId
    private Long id;

    @StartNode
    private StopNode stopFrom;

    @EndNode
    private StopNode stopTo;

    @GraphProperty
    private Long waitingTime;

    public NextAwaitingStopRelationship() {

    }

    public NextAwaitingStopRelationship(StopNode stopFrom, StopNode stopTo, Long waitingTime) {
        this.stopFrom = stopFrom;
        this.stopTo = stopTo;
        this.waitingTime = waitingTime;
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

    public Long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Long waitingTime) {
        this.waitingTime = waitingTime;
    }
}
