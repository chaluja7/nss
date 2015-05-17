package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;
import java.util.Set;

/**
 * Stop Neo4j repository.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jRepository extends GraphRepository<StopNode> {

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
     * najde stop dle stopId
     * @param stopId stopId
     * @return stopNode dle stopId
     */
    @Query("match (n:StopNode {stopId: {0}}) return n")
    StopNode findByStopId(Long stopId);

    /**
     * najde stopy dle rideId
     * @param rideId rideId
     * @return stopy dle rideId
     */
    @Query("match (n:StopNode {rideId: {0}}) return n")
    Set<StopNode> findByRideId(Long rideId);

    /**
     * vrati serazene stopy nalezici jedne stanici. Vyuziva se pro propojovani stopu na stanici hranamy prestupnimi
     * @param stationId id stanice
     * @return serazene stopy na stanici dle casu
     */
    @Query("match (n:StopNode {stationId: {0}}) return n order by case when n.departureInMillis is not null then n.departureInMillis else n.arrivalInMillis end")
    List<StopNode> findStopNodesByStationIdOrderByActionTime(Long stationId);

}
