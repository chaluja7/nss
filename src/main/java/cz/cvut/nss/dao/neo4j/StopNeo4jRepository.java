package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.neo4j.graphdb.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Set;

/**
 * Stop Neo4j repository.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jRepository extends GraphRepository<StopNode> {

    /**
     * hledani trasy bez prestupu
     * @param stationFrom id stanice z
     * @param stationTo id stanice do
     * @param departureInMillisMin datum odjezdu
     * @param departureInMillisMax max datum odjezdu
     * @return nalezene cesty
     */
    @Query("match path = ((n:StopNode {stationId: {0}})-[NEXT_STOP*]->(m:StopNode {stationId: {1}})) where n.departureInMillis >= {2} and n.departureInMillis <= {3} return path")
    Iterable<EntityPath<StopNode, StopNode>> getAllPathsWithoutTransfers(Long stationFrom, Long stationTo, Long departureInMillisMin, Long departureInMillisMax);

    /**
     * nalezne vsechny stopy na dane stanici, ktere nemaji null prijezd, serazene dle prijezdu
     * @param stationId id stanice
     * @return nalezene stopy
     */
    @Query("match (n:StopNode {stationId: {0}}) where n.arrivalInMillis is not null return n")
    Set<StopNode> findNotStartingStopNodesByStationOrderByArrival(Long stationId);

    /**
     * najde vsechny stopy na dane stanici, ktere maji odjezd v intervalu argumentu
     * @param stationId id stanice
     * @param departureInMillisMin min datum odjezdu
     * @param departureInMillisMax max datum odjezdu
     * @return nalezene stopy
     */
    @Query("match (n:StopNode {stationId: {0}}) where n.departureInMillis >= {1} and n.departureInMillis <= {2} return n")
    Set<StopNode> findByStationAndDepartureRange(Long stationId, Long departureInMillisMin, Long departureInMillisMax);

    /**
     * najde vsechny stopy dle stationId a rozsahu departureInMillis
     * @param stationId id stanice
     * @param departureInMillisMin min datum odjezdu
     * @param departureInMillisMax max datum odjezdu
     * @return nalezene stopy
     */
    @Query("match (n:StopNode {stationId: {0}}) where n.departureInMillis >= {1} and n.departureInMillis <= {2} return n")
    Iterable<Node> findByStationAndDepartureRangeReturnIterable(Long stationId, Long departureInMillisMin, Long departureInMillisMax);

    /**
     * najde vsechny stopy dle stationId a rozsahu arrivalInMillis
     * @param stationId id stanice
     * @param arrivalInMillisMin min datum prijezdu
     * @param arrivalInMillisMax max datum prijezdu
     * @return nalezene stopy
     */
    @Query("match (n:StopNode {stationId: {0}}) where n.arrivalInMillis >= {1} and n.arrivalInMillis <= {2} return n")
    Iterable<Node> findByStationAndArrivalRangeReturnIterable(Long stationId, Long arrivalInMillisMin, Long arrivalInMillisMax);

    @Query("match (n:StopNode {stopId: {0}}) return n")
    StopNode findByStopId(Long stopId);

    @Query("match (n:StopNode {rideId: {0}}) return n")
    Set<StopNode> findByRideId(Long rideId);

}
