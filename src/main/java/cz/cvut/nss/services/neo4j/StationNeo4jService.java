package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.entities.neo4j.StationNode;

/**
 * @author jakubchalupa
 * @since 15.03.15
 */
public interface StationNeo4jService {

    StationNode create(StationNode station);

    Iterable<StationNode> findAll();

    StationNode findById(long id);

    void deleteAll();

    StationNode findOneByLongProperty(String propertyName, Long propertyValue);

}
