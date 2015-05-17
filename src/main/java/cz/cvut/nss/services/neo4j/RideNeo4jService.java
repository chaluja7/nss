package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.RideNode;

/**
 * Ride Neo4j service.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface RideNeo4jService {

    /**
     * ulozi RideNode
     * @param rideNode RideNode
     * @return ulozeny RideNode
     */
    RideNode save(RideNode rideNode);

    /**
     * najde RideNode dle id uzlu
     * @param id id uzlu
     * @return RideNode dle id uzlu
     */
    RideNode findById(long id);

    /**
     * @return vsechny RideNode
     */
    Iterable<RideNode> findAll();

    /**
     * smaze vsechny RideNode
     */
    void deleteAll();

}
