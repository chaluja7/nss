package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.dao.neo4j.StopNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jakubchalupa
 * @since 17.03.15
 */
@Service
@Transactional("neo4jTransactionManager")
public class StopNeo4jServiceImpl implements StopNeo4jService {

    @Autowired
    protected StopNeo4jRepository stopNeo4jRepository;

    @Override
    public StopNode create(StopNode stop) {
        return stopNeo4jRepository.save(stop);
    }

    @Override
    public Iterable<StopNode> findAll() {
        return stopNeo4jRepository.findAll();
    }

    @Override
    public StopNode findById(long id) {
        return stopNeo4jRepository.findOne(id);
    }

    @Override
    public void deleteAll() {
        stopNeo4jRepository.deleteAll();
    }

}
