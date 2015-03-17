package cz.cvut.nss.dao.neo4j;

import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jRepository extends GraphRepository<StopNode> {

}
