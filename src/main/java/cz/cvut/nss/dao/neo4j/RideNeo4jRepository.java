package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.RideNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Ride Neo4j repository.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface RideNeo4jRepository extends GraphRepository<RideNode> {

}
