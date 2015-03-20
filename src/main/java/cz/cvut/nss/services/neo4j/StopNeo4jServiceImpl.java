package cz.cvut.nss.services.neo4j;

import cz.cvut.nss.dao.neo4j.StopNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StopNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public void testFindPath() {
        //najde vsechny cesty! :)

        Iterable<EntityPath<StopNode, StopNode>> shortestNetworkPathBetween = stopNeo4jRepository.getShortestNetworkPathBetween(new Long(1), new Long(7), new Long(1418011560000l), new Long(1418060160000l));

        List<StopNode> stopNodeList = new ArrayList<>();
        Iterator<EntityPath<StopNode, StopNode>> iterator = shortestNetworkPathBetween.iterator();
        while(iterator.hasNext()) {
            EntityPath<StopNode, StopNode> next = iterator.next();

            Iterator<Object> nodeEntitiesIterator = next.nodeEntities().iterator();
            while(nodeEntitiesIterator.hasNext()) {
                StopNode stopNode = (StopNode) nodeEntitiesIterator.next();
                int i = 0;
            }
        }

        int j = 0;
    }

}
