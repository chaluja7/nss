package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.dao.neo4j.StationNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StationNode;
import cz.cvut.nss.services.neo4j.impl.StationNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

/**
 * Station Neo4j service implementation.
 *
 * @author jakubchalupa
 * @since 15.03.15
 */
@Service
@Transactional("neo4jTransactionManager")
public class StationNeo4jServiceImpl implements StationNeo4jService {

    @Autowired
    protected StationNeo4jRepository stationNeo4jRepository;

    @Override
    public StationNode create(StationNode station) {
        return stationNeo4jRepository.save(station);
    }

    @Override
    public Iterable<StationNode> findAll() {
        return stationNeo4jRepository.findAll();
    }

    @Override
    public StationNode findById(long id) {
        return stationNeo4jRepository.findOne(id);
    }

    @Override
    public void deleteAll() {
        stationNeo4jRepository.deleteAll();
    }

    @Override
    public StationNode findOneByLongProperty(String propertyName, Long propertyValue) {
        Iterable<StationNode> stationNodeIterable = stationNeo4jRepository.findAllBySchemaPropertyValue(propertyName, propertyValue);

        Iterator<StationNode> stationNodeIterator = stationNodeIterable.iterator();
        if(stationNodeIterator.hasNext()) {
            return stationNodeIterator.next();
        }

        return null;
    }

}
