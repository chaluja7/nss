package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.StopNode;

/**
 * @author jakubchalupa
 * @since 17.03.15
 */
public interface StopNeo4jService {

    StopNode create(StopNode stop);

    Iterable<StopNode> findAll();

    StopNode findById(long id);

    void deleteAll();

    void testFindPath();

}
