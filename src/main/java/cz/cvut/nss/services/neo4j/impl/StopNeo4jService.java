package cz.cvut.nss.services.neo4j.impl;

import cz.cvut.nss.entities.neo4j.StopNode;

import java.util.Set;

/**
 * Stop Neo4j service.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jService {

    StopNode save(StopNode stop);

    Iterable<StopNode> findAll();

    StopNode findById(long id);

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

}
