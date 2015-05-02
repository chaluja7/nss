package cz.cvut.nss.dao.neo4j.impl;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.dao.jdbc.JdbcSearchDao;
import cz.cvut.nss.dao.neo4j.StopNeo4jRepository;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.entities.neo4j.relationship.RelTypes;
import cz.cvut.nss.utils.DateTimeUtils;
import cz.cvut.nss.utils.evaluator.*;
import org.joda.time.LocalDateTime;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.InitialBranchState;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
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

    @Autowired
    protected Neo4jTemplate neo4jTemplate;

    @Override
    public List<SearchResultWrapper> findRidesByDepartureDate(long stationFromId, long stationToId, Date departure, Date maxDeparture, int maxTransfers) {

        InitialBranchState<StationRideWrapper> initialBranchState = new InitialBranchState<StationRideWrapper>() {

            @Override
            public InitialBranchState<StationRideWrapper> reverse() {
                return this;
            }

            @Override
            public StationRideWrapper initialState(Path path) {
                return new StationRideWrapper();
            }

        };

        TraversalDescription traversalDescription = graphDatabaseService.traversalDescription()
                .order(CustomBranchOrderingPolicies.CUSTOM_ORDERING)
                .uniqueness(Uniqueness.NODE_PATH)
                .expand(new CustomExpander(new LocalDateTime(departure), new LocalDateTime(maxDeparture), maxTransfers), initialBranchState)
                .evaluator(new EndStopNodeEvaluator(stationToId, new LocalDateTime(departure), EndStopNodeEvaluatorType.DEPARTURE, 3));
        long la = System.currentTimeMillis();
        Iterable<Node> startNodes = this.findStartNodes(stationFromId, departure, maxDeparture);

//        for(Node n : startNodes) {
//            int i = 0;
//        }

        la = System.currentTimeMillis() - la;
        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();
        la = System.currentTimeMillis();

        int millisOfDepartureDay = new LocalDateTime(departure).getMillisOfDay();
        Traverser traverser = traversalDescription.traverse(startNodes);
        for(Path path : traverser) {
            Node startNode = path.startNode();
            Node endNode = path.endNode();

            long millisOfArrival = ((long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY));
            long millisOfDeparture = ((long) startNode.getProperty(StopNode.DEPARTURE_PROPERTY));
            long travelTime;
            if(millisOfArrival > millisOfDeparture) {
                travelTime = millisOfArrival - millisOfDeparture;
            } else {
                travelTime = (DateTimeUtils.MILLIS_IN_DAY - millisOfDeparture) + millisOfArrival;
            }

            boolean overMidnightDeparture = false;
            if(millisOfDeparture < millisOfDepartureDay) {
                overMidnightDeparture = true;
            }

            boolean overMidnightArrival = false;
            if(millisOfArrival < millisOfDepartureDay) {
                overMidnightArrival = true;
            }

            //vyberu ridy, po kterych jede cesta
            List<Long> ridesOnPath = new ArrayList<>();
            List<Long> stopsOnPath = new ArrayList<>();
            RelTypes prevRelationshipType = null;
            for(Relationship relationship : path.relationships()) {
                Node relationshipStartNode = relationship.getStartNode();
                long nodeStopProperty = (long) relationshipStartNode.getProperty(StopNode.STOP_PROPERTY);
                long nodeRideProperty = (long) relationshipStartNode.getProperty(StopNode.RIDE_PROPERTY);

                if(prevRelationshipType != null && prevRelationshipType.equals(RelTypes.NEXT_STOP) && relationship.isType(RelTypes.NEXT_AWAITING_STOP)) {
                    stopsOnPath.add(nodeStopProperty);
                }

                if(relationship.isType(RelTypes.NEXT_STOP) && !ridesOnPath.contains(nodeRideProperty)) {
                    stopsOnPath.add(nodeStopProperty);
                    ridesOnPath.add(nodeRideProperty);
                }

                if(relationship.isType(RelTypes.NEXT_AWAITING_STOP)) {
                    prevRelationshipType = RelTypes.NEXT_AWAITING_STOP;
                } else {
                    prevRelationshipType = RelTypes.NEXT_STOP;
                }
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
                wrapper.setDeparture(millisOfDeparture);
                wrapper.setArrival(millisOfArrival);
                wrapper.setTravelTime(travelTime);
                wrapper.setOverMidnightDeparture(overMidnightDeparture);
                wrapper.setOverMidnightArrival(overMidnightArrival);
                wrapper.setStops(stopsOnPath);
                ridesMap.put(pathIdentifier, wrapper);
            }

        }

        la = System.currentTimeMillis() - la;

        //vysledky vyhledavani dam do listu a vratim. momentalne tam jsou vysledky, ktere dale musi byt vyfiltrovany!
        return transformSearchResultWrapperMapToList(ridesMap);
    }

    @Override
    public List<SearchResultWrapper> findRidesByArrivalDate(long stationFromId, long stationToId, Date arrival, Date minArrival, int maxTransfers) {

        return new ArrayList<>();

//        TraversalDescription traversalDescription = graphDatabaseService.traversalDescription()
//                .breadthFirst()
//                .relationships(RelTypes.NEXT_STOP, Direction.INCOMING)
//                .relationships(RelTypes.NEXT_AWAITING_STOP, Direction.INCOMING)
//                .uniqueness(Uniqueness.NODE_PATH)
//                .order(BranchOrderingPolicies.PREORDER_BREADTH_FIRST)
//                .evaluator(new EndStopNodeEvaluator(stationFromId, minArrival.getTime(), maxTransfers, EndStopNodeEvaluatorType.ARRIVAL));
//
//        Iterable<Node> iterable = stopNeo4jRepository.findByStationAndArrivalRangeReturnIterable(stationToId, minArrival.getTime(), arrival.getTime());
//
//        Map<String, SearchResultWrapper> ridesMap = new HashMap<>();
//        Traverser traverser = traversalDescription.traverse(iterable);
//        for(Path path : traverser) {
//            Node startNode = path.endNode();
//            Node endNode = path.startNode();
//
//            long travelTime = (long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY) - (long) startNode.getProperty(StopNode.DEPARTURE_PROPERTY);
//
//            //vyberu ridy, po kterych jede cesta
//            List<Long> ridesOnPath = new ArrayList<>();
//            List<Long> stopsOnPath = new ArrayList<>();
//            Node prevNode = null;
//            for(Node node : path.reverseNodes()) {
//                long rideId = (long) node.getProperty(StopNode.RIDE_PROPERTY);
//
//                if(ridesOnPath.size() == 0 || ridesOnPath.get(ridesOnPath.size() - 1) != rideId) {
//                    if(ridesOnPath.size() == 0) {
//                        stopsOnPath.add((long) node.getProperty(StopNode.STOP_PROPERTY));
//                    } else {
//                        stopsOnPath.add((long) prevNode.getProperty(StopNode.STOP_PROPERTY));
//                        stopsOnPath.add((long) node.getProperty(StopNode.STOP_PROPERTY));
//                    }
//
//                    ridesOnPath.add(rideId);
//                }
//
//                prevNode = node;
//            }
//
//            long lastStopId = (long) endNode.getProperty(StopNode.STOP_PROPERTY);
//            if(stopsOnPath.get(stopsOnPath.size() - 1) != lastStopId) {
//                stopsOnPath.add(lastStopId);
//            }
//
//            StringBuilder stringBuilder = new StringBuilder();
//            for(Long l : ridesOnPath) {
//                if(stringBuilder.length() != 0) {
//                    stringBuilder.append("-");
//                }
//                stringBuilder.append(l);
//            }
//
//            String pathIdentifier = stringBuilder.toString();
//            if(!ridesMap.containsKey(pathIdentifier) || travelTime < ridesMap.get(pathIdentifier).getTravelTime()) {
//                SearchResultWrapper wrapper = new SearchResultWrapper();
//                wrapper.setArrival((long) endNode.getProperty(StopNode.ARRIVAL_PROPERTY));
//                wrapper.setTravelTime(travelTime);
//                wrapper.setStops(stopsOnPath);
//                ridesMap.put(pathIdentifier, wrapper);
//            }
//
//        }
//
//        //vysledky vyhledavani dam do listu a vratim. momentalne tam jsou vysledky, ktere dale musi byt vyfiltrovany!
//        return transformSearchResultWrapperMapToList(ridesMap);
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

    protected Iterable<Node> findStartNodes(long stationId, Date departure, Date maxDateDeparture) {
        LocalDateTime tempDateDeparture = new LocalDateTime(departure);
        LocalDateTime tempMaxDateDeparture = new LocalDateTime(maxDateDeparture);

        Map<String, Object> params = new HashMap<>();
        params.put("stationId", stationId);
        params.put("departureDateInMillis", departure.getTime());
        params.put("departureDateMinusOneDayInMillis", departure.getTime() - DateTimeUtils.MILLIS_IN_DAY);
        params.put("trueParameter", true);
        params.put("departureTimeInMillis", tempDateDeparture.getMillisOfDay());
        params.put("maxDepartureTimeInMillis", tempMaxDateDeparture.getMillisOfDay());

        String queryString = "match (s:StopNode {stationId: {stationId}})-[IN_RIDE]->(r:RideNode)-[IN_INTERVAL]->(i:OperationIntervalNode) ";
        queryString += "where s.departureInMillis is not null ";

        if(tempMaxDateDeparture.getMillisOfDay() > tempDateDeparture.getMillisOfDay()) {
            //neprehoupl jsem se pres pulnoc
            queryString += "and i.fromDateInMillis <= {departureDateInMillis} and i.toDateInMillis >= {departureDateMinusOneDayInMillis} ";
            queryString += "and " + JdbcSearchDao.getDayOfWeekCondition(tempDateDeparture.getDayOfWeek()) + " = {trueParameter} ";
            queryString += "and s.departureInMillis >= {departureTimeInMillis} and s.departureInMillis < {maxDepartureTimeInMillis} ";
        } else {
            //prehoupl jsem se s rozsahem pres pulnoc
            queryString += "and ((i.fromDateInMillis <= {departureDateInMillis} and i.toDateInMillis >= {departureDateMinusOneDayInMillis} ";
            queryString += "and " + JdbcSearchDao.getDayOfWeekCondition(tempDateDeparture.getDayOfWeek()) + " = {trueParameter} ";
            queryString += "and s.departureInMillis >= {departureTimeInMillis}) ";

            queryString += "or (i.fromDateInMillis <= {maxDepartureDateInMillis} and i.toDateInMillis >= {maxDepartureDateMinusOneDayInMillis} ";
            queryString += "and  " + JdbcSearchDao.getDayOfWeekCondition(tempMaxDateDeparture.getDayOfWeek()) + " = {trueParameter} ";
            queryString += "and s.departureInMillis < {maxDepartureTimeInMillis})) ";

            params.put("maxDepartureDateInMillis", maxDateDeparture.getTime());
            params.put("maxDepartureDateMinusOneDayInMillis", maxDateDeparture.getTime() - DateTimeUtils.MILLIS_IN_DAY);
        }

        queryString += "return s order by case when s.departureInMillis < {departureTimeInMillis} then 2 else 1 end, s.departureInMillis asc";

        Result<Map<String, Object>> query = neo4jTemplate.query(queryString, params);
        return query.to(Node.class);
    }

}
