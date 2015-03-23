package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

/**
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jRepository extends GraphRepository<StopNode>, RelationshipOperationsRepository<StopNode> {

    @Query("match path = ((n:StopNode {stationId: {0}})-[NEXT_STOP*]->(m:StopNode {stationId: {1}})) where n.departureInMillis >= {2} and n.departureInMillis <= {3} return path")
    Iterable<EntityPath<StopNode, StopNode>> getShortestNetworkPathBetween(Long stationFrom, Long stationTo, Long departureInMillisMin, Long departureInMillisMax);

}
