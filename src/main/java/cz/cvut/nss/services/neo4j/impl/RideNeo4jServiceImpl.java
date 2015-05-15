package cz.cvut.nss.services.neo4j.impl;

import cz.cvut.nss.dao.neo4j.RideNeo4jRepository;
import cz.cvut.nss.entities.neo4j.RideNode;
import cz.cvut.nss.services.neo4j.RideNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stop Neo4j service implementation.
 *
 * @author jakubchalupa
 * @since 17.03.15
 */
@Service
@Transactional("neo4jTransactionManager")
public class RideNeo4jServiceImpl implements RideNeo4jService {

    @Autowired
    protected RideNeo4jRepository rideNeo4jRepository;

    @Override
    public RideNode save(RideNode rideNode) {
        return rideNeo4jRepository.save(rideNode);
    }

    @Override
    public RideNode findById(long id) {
        return rideNeo4jRepository.findOne(id);
    }

    @Override
    public Iterable<RideNode> findAll() {
        return rideNeo4jRepository.findAll();
    }

    @Override
    public void deleteAll() {
        rideNeo4jRepository.deleteAll();
    }
}
