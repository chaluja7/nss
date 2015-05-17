package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.StopNode;

import java.util.List;
import java.util.Set;

/**
 * Stop Neo4j service.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jService {

    /**
     * ulozi StopNode
     * @param stop StopNode k ulozeni
     * @return ulozeny StopNode
     */
    StopNode save(StopNode stop);

    /**
     * @return vsechny StopNode
     */
    Iterable<StopNode> findAll();

    /**
     * najde StopNode dle id uzlu
     * @param id id uzlu
     * @return StopNode dle id uzlu
     */
    StopNode findById(long id);

    /**
     * najde StopNode dle stopId
     * @param stopId stopId
     * @return StopNode dle stopid
     */
    StopNode findByStopId(long stopId);

    /**
     * smaze vsechny StopNode
     */
    void deleteAll();

    /**
     * nalezne vsechny stopy na dane stanici, ktere nemaji null prijezd, serazene dle prijezdu
     * @param stationId id stanice
     * @return nalezene stopy
     */
    Set<StopNode> findNotStartingStopNodesByStationOrderByArrival(Long stationId);

    /**
     * najde vsechny stopy na dane stanici, ktere maji odjezd v intervalu argumentu
     * @param stationId id stanice
     * @param departureInMillisMin min datum odjezdu
     * @param departureInMillisMax max datum odjezdu
     * @return nalezene stopy
     */
    Set<StopNode> findByStationAndDepartureRange(Long stationId, Long departureInMillisMin, Long departureInMillisMax);

    /**
     * propoji stopy v neo4j prestupnymi hranami (tedy v ramci jedne stanice)
     * @param stationId id stanice
     */
    void connectStopNodesOnStationWithWaitingStopRelationship(long stationId);

    /**
     * smaze StopNode dle stopId
     * @param stopId stopId
     */
    void deleteByStopId(long stopId);

    /**
     * smaze vsechny StopNode na ride (tripu)
     * @param rideId rideId (tripId)
     */
    void deleteAllByRideId(long rideId);

    /**
     * najde vsechny StopNode na stanici serazene dle absolutniho casu
     * @param stationId id stanice
     * @return vsechny StopNode na dane stanici dle absolutniho casu
     */
    List<StopNode> findStopNodesByStationIdOrderByActionTime(Long stationId);

}
