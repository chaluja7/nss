package cz.cvut.nss.entities.neo4j;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

/**
 * @author jakubchalupa
 * @since 18.04.15
 */
@NodeEntity
public class RideNode {

    @GraphId
    private Long id;

    @Indexed(unique = true)
    @GraphProperty
    private Long rideId;

    @RelatedTo(type = "IN_INTERVAL", direction = Direction.OUTGOING)
    private OperationIntervalNode operationIntervalNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public OperationIntervalNode getOperationIntervalNode() {
        return operationIntervalNode;
    }

    public void setOperationIntervalNode(OperationIntervalNode operationIntervalNode) {
        this.operationIntervalNode = operationIntervalNode;
    }
}
