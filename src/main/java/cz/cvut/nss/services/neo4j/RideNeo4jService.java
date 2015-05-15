package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.RideNode;

/**
 * Stop Neo4j service.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface RideNeo4jService {

    RideNode save(RideNode rideNode);

    Iterable<RideNode> findAll();

    void deleteAll();

}
