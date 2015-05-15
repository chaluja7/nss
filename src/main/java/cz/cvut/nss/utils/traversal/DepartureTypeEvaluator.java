package cz.cvut.nss.utils.traversal;

import com.google.common.collect.Sets;
import cz.cvut.nss.entities.neo4j.StopNode;
import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import java.util.*;

/**
 * Evaluator for neo4j traversal (finding paths to end station)
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public final class DepartureTypeEvaluator implements Evaluator {

    private final Long endStationId;

    private final int departureMillisOfDay;

    private final int maxNumberOfResults;

    Map<Long, Long> foundedPaths = new HashMap<>();

    Map<Long, Integer> foundedPathsNumOfTransfers = new HashMap<>();

    Map<Long, Set<Long>> foundedPathsDetails = new HashMap<>();

    Long prevFoundedDeparture = null;

    public DepartureTypeEvaluator(Long endStationId, LocalDateTime departureDateTime, int maxNumberOfResults) {
        this.endStationId = endStationId;
        this.departureMillisOfDay = departureDateTime.getMillisOfDay();
        this.maxNumberOfResults = maxNumberOfResults;
    }

    @Override
    public Evaluation evaluate(Path path) {
        Node startNode = path.startNode();
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);
        long startNodeDeparture = (long) startNode.getProperty(StopNode.DEPARTURE_PROPERTY);

        Node currentNode = path.endNode();
        long currentNodeStationId = (long) currentNode.getProperty(StopNode.STATION_PROPERTY);

        //rozhodujici je arrival time pokud existuje, jinak departure time
        Long currentNodeArrival = null;
        Long currentNodeDeparture = null;
        if (currentNode.hasProperty(StopNode.ARRIVAL_PROPERTY)) {
            currentNodeArrival = (Long) currentNode.getProperty(StopNode.ARRIVAL_PROPERTY);
        }
        if (currentNode.hasProperty(StopNode.DEPARTURE_PROPERTY)) {
            currentNodeDeparture = (Long) currentNode.getProperty(StopNode.DEPARTURE_PROPERTY);
        }

        if(currentNodeArrival == null && currentNodeDeparture == null) {
            throw new RuntimeException();
        }

        Long currentNodeTimeProperty = currentNodeArrival != null ? currentNodeArrival : currentNodeDeparture;

        //POKUD jsem jiz na teto ceste (od start node) nasel cil v lepsim case
        if(foundedPaths.containsKey(startNodeStopId)) {
            Long prevBestFoundedPathStart = foundedPaths.get(startNodeStopId);
            long currentNodeMillisTimeWithPenalty = currentNodeTimeProperty + (DateTimeUtils.TRANSFER_PENALTY_MILLIS * foundedPathsNumOfTransfers.get(startNodeStopId));
            if(currentNodeMillisTimeWithPenalty >= DateTimeUtils.MILLIS_IN_DAY) {
                //prehoupl jsem se s penalizaci do dalsiho dne
                currentNodeMillisTimeWithPenalty = currentNodeMillisTimeWithPenalty - DateTimeUtils.MILLIS_IN_DAY;
            }

            if(prevBestFoundedPathStart >= departureMillisOfDay) {
                //minuly nejlepsi cil byl pred pulnoci
                if((currentNodeMillisTimeWithPenalty > prevBestFoundedPathStart && currentNodeMillisTimeWithPenalty >= departureMillisOfDay) || currentNodeMillisTimeWithPenalty < departureMillisOfDay) {
                    //momentalne jsem taky pred pulnoci ale v horsim case nebo jsem az po pulnoci
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            } else {
                //minuly nejlepsi cil byl po pulnoci
                if(currentNodeMillisTimeWithPenalty > prevBestFoundedPathStart && currentNodeMillisTimeWithPenalty < departureMillisOfDay) {
                    //momentalne jsem taky po pulnoci ale s horsim casem
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            }
        }

        //vyhledavam na ceste, kde departureTime (vychoziho uzlu) je mensi, nez departure time nektere jiz nalezene cesty
        //to muzu hned ukoncit, protoze takovy vysledek stejne uzivateli nezobrazim
        if(prevFoundedDeparture != null && startNodeDeparture < prevFoundedDeparture) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //uz jsem nasel pozadovany pocet vysledku
        if(foundedPathsDetails.size() >= maxNumberOfResults) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //nasel jsem
        if(currentNodeStationId == endStationId) {
            Set<Long> tmpRides = new HashSet<>();
            for(Node n : path.nodes()) {
                tmpRides.add((long) n.getProperty(StopNode.RIDE_PROPERTY));
            }

            long currentNodeMillisTimeWithPenalty = currentNodeTimeProperty + (DateTimeUtils.TRANSFER_PENALTY_MILLIS * tmpRides.size() - 1);
            if(currentNodeMillisTimeWithPenalty >= DateTimeUtils.MILLIS_IN_DAY) {
                //prehoupl jsem se s penalizaci do dalsiho dne
                currentNodeMillisTimeWithPenalty = currentNodeMillisTimeWithPenalty - DateTimeUtils.MILLIS_IN_DAY;
            }

            boolean saveMe = true;
            List<Long> keysToRemove = new ArrayList<>();
            for (Map.Entry<Long, Set<Long>> entry : foundedPathsDetails.entrySet()) {
                Long key = entry.getKey();
                Set<Long> value = entry.getValue();

                if(!Sets.intersection(tmpRides, value).isEmpty()) {
                    Long pathArrival = foundedPaths.get(key);
                    if(pathArrival >= departureMillisOfDay) {
                        //cil aktualni cesty byl pred pulnoci
                        if((currentNodeMillisTimeWithPenalty >= departureMillisOfDay && currentNodeMillisTimeWithPenalty > pathArrival) || currentNodeMillisTimeWithPenalty < departureMillisOfDay) {
                            //momentalne jsem v cili taky pred pulnoci, ale s horsim casem nez jsem jiz byl, nebo jsem v cili az po pulnoci
                            saveMe = false;
                            break;
                        } else {
                            keysToRemove.add(key);
                        }
                    } else {
                        //cil aktualni cesty byl po pulnoci
                        if(currentNodeMillisTimeWithPenalty < departureMillisOfDay && currentNodeMillisTimeWithPenalty > pathArrival) {
                            //momentalne jsem taky po pulnoci ale pozdeji
                            saveMe = false;
                            break;
                        } else {
                            keysToRemove.add(key);
                        }
                    }
                }
            }

            for(Long l : keysToRemove) {
                foundedPathsDetails.remove(l);
            }

            if(saveMe) {
                foundedPathsDetails.put(startNodeStopId, tmpRides);
            }

            prevFoundedDeparture = startNodeDeparture;
            foundedPaths.put(startNodeStopId, currentNodeMillisTimeWithPenalty);
            foundedPathsNumOfTransfers.put(startNodeStopId, tmpRides.size() - 1);
            return Evaluation.INCLUDE_AND_PRUNE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
