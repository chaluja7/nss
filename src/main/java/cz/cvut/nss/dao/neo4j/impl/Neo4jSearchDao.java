package cz.cvut.nss.dao.neo4j.impl;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.dao.neo4j.StopNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import cz.cvut.nss.utils.evaluator.EndStopNodeEvaluator;
import cz.cvut.nss.utils.evaluator.EndStopNodeEvaluatorType;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.BranchOrderingPolicies;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Neo4j implementace vyhledavani spoju
 *
 * @author jakubchalupa
 * @since 31.03.15
 */
@Repository
public class Neo4jSearchDao implements SearchDao {

    @Autowired
    protected GraphDatabaseService graphDatabaseService;

    @Autowired
    protected StopNeo4jRepository stopNeo4jRepository;

    @Override
    public List<SearchResultWrapper> findRidesByDepartureDate(long stationFromId, long stationToId, Date departure, Date maxDeparture, int maxTransfers) {

        TraversalDescription traversalDescription = graphDatabaseService.traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.NEXT_STOP, Direction.OUTGOING)
                .relationships(RelTypes.NEXT_AWAITING_STOP, Direction.OUTGOING)
                .uniqueness(Uniqueness.NODE_PATH)
                .order(BranchOrderingPolicies.PREORDER_BREADTH_FIRST)
                .evaluator(new EndStopNodeEvaluator(stationToId, maxDeparture.getTime(), maxTransfers, EndStopNodeEvaluatorType.DEPARTURE));


        Iterable<Node> iterable = stopNeo4jRepository.findByStationAndDepartureRangeReturnIterable(stationFromId, departure.getTime(), maxDeparture.getTime());

        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();
        Traverser traverser = traversalDescription.traverse(iterable);
        for(Path path : traverser) {
            Node startNode = path.startNode();
            Node endNode = path.endNode();

            long travelTime = (long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY) - (long) startNode.getProperty(StopNode.DEPARTURE_PROPERTY);

            //vyberu ridy, po kterych jede cesta
            List<Long> ridesOnPath = new ArrayList<>();
            List<Long> stopsOnPath = new ArrayList<>();
            Node prevNode = null;
            for(Node node : path.nodes()) {
                long rideId = (long) node.getProperty(StopNode.RIDE_PROPERTY);

                if(ridesOnPath.size() == 0 || ridesOnPath.get(ridesOnPath.size() - 1) != rideId) {
                    if(ridesOnPath.size() == 0) {
                        stopsOnPath.add((long) node.getProperty(StopNode.STOP_PROPERTY));
                    } else {
                        stopsOnPath.add((long) prevNode.getProperty(StopNode.STOP_PROPERTY));
                        stopsOnPath.add((long) node.getProperty(StopNode.STOP_PROPERTY));
                    }

                    ridesOnPath.add(rideId);
                }

                prevNode = node;
            }

            long lastStopId = (long) endNode.getProperty(StopNode.STOP_PROPERTY);
            if(stopsOnPath.get(stopsOnPath.size() - 1) != lastStopId) {
                stopsOnPath.add(lastStopId);
            }

            StringBuilder stringBuilder = new StringBuilder();
            for(Long l : ridesOnPath) {
                if(stringBuilder.length() != 0) {
                    stringBuilder.append("-");
                }
                stringBuilder.append(l);
            }

            String pathIdentifier = stringBuilder.toString();
            if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
                SearchResultWrapper wrapper = new SearchResultWrapper();
                wrapper.setArrival((long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY));
                wrapper.setTravelTime(travelTime);
                wrapper.setStops(stopsOnPath);
                ridesMap.put(pathIdentifier, wrapper);
            }

        }

        //vysledky vyhledavani dam do listu a vratim. momentalne tam jsou vysledky, ktere dale musi byt vyfiltrovany!
        return transformSearchResultWrapperMapToList(ridesMap);
    }

    @Override
    public List<SearchResultWrapper> findRidesByArrivalDate(long stationFromId, long stationToId, Date arrival, Date minArrival, int maxTransfers) {

        TraversalDescription traversalDescription = graphDatabaseService.traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.NEXT_STOP, Direction.INCOMING)
                .relationships(RelTypes.NEXT_AWAITING_STOP, Direction.INCOMING)
                .uniqueness(Uniqueness.NODE_PATH)
                .order(BranchOrderingPolicies.PREORDER_BREADTH_FIRST)
                .evaluator(new EndStopNodeEvaluator(stationFromId, minArrival.getTime(), maxTransfers, EndStopNodeEvaluatorType.ARRIVAL));

        Iterable<Node> iterable = stopNeo4jRepository.findByStationAndArrivalRangeReturnIterable(stationToId, minArrival.getTime(), arrival.getTime());

        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();
        Traverser traverser = traversalDescription.traverse(iterable);
        for(Path path : traverser) {
            Node startNode = path.endNode();
            Node endNode = path.startNode();

            long travelTime = (long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY) - (long) startNode.getProperty(StopNode.DEPARTURE_PROPERTY);

            //vyberu ridy, po kterych jede cesta
            List<Long> ridesOnPath = new ArrayList<>();
            List<Long> stopsOnPath = new ArrayList<>();
            Node prevNode = null;
            for(Node node : path.reverseNodes()) {
                long rideId = (long) node.getProperty(StopNode.RIDE_PROPERTY);

                if(ridesOnPath.size() == 0 || ridesOnPath.get(ridesOnPath.size() - 1) != rideId) {
                    if(ridesOnPath.size() == 0) {
                        stopsOnPath.add((long) node.getProperty(StopNode.STOP_PROPERTY));
                    } else {
                        stopsOnPath.add((long) prevNode.getProperty(StopNode.STOP_PROPERTY));
                        stopsOnPath.add((long) node.getProperty(StopNode.STOP_PROPERTY));
                    }

                    ridesOnPath.add(rideId);
                }

                prevNode = node;
            }

            long lastStopId = (long) endNode.getProperty(StopNode.STOP_PROPERTY);
            if(stopsOnPath.get(stopsOnPath.size() - 1) != lastStopId) {
                stopsOnPath.add(lastStopId);
            }

            StringBuilder stringBuilder = new StringBuilder();
            for(Long l : ridesOnPath) {
                if(stringBuilder.length() != 0) {
                    stringBuilder.append("-");
                }
                stringBuilder.append(l);
            }

            String pathIdentifier = stringBuilder.toString();
            if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
                SearchResultWrapper wrapper = new SearchResultWrapper();
                wrapper.setArrival((long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY));
                wrapper.setTravelTime(travelTime);
                wrapper.setStops(stopsOnPath);
                ridesMap.put(pathIdentifier, wrapper);
            }

        }

        //vysledky vyhledavani dam do listu a vratim. momentalne tam jsou vysledky, ktere dale musi byt vyfiltrovany!
        return transformSearchResultWrapperMapToList(ridesMap);
    }

    /**
     * vrati list value hodnot z mapy
     * @param ridesMap mapa search result wrapperu
     * @return list search result wrapperu
     */
    protected List<SearchResultWrapper> transformSearchResultWrapperMapToList(Map<String, SearchResultWrapper> ridesMap) {
        List<SearchResultWrapper> resultList = new ArrayList<>();
        for(Map.Entry<String, SearchResultWrapper> entry : ridesMap.entrySet()) {
            resultList.add(entry.getValue());
        }

        return resultList;
    }

}
