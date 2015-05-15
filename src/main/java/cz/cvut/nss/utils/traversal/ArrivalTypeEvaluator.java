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
 * Evaluator for neo4j traversal (finding paths from end station)
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public final class ArrivalTypeEvaluator implements Evaluator {

    private final Long startStationId;

    private final int arrivalMillisOfDay;

    private final int maxNumberOfResults;

    Map<Long, Long> foundedPaths = new HashMap<>();

    Map<Long, Integer> foundedPathsNumOfTransfers = new HashMap<>();

    Map<Long, Set<Long>> foundedPathsDetails = new HashMap<>();

    Long prevFoundedArrival = null;

    public ArrivalTypeEvaluator(Long startStationId, LocalDateTime arrivalDateTime, int maxNumberOfResults) {
        this.startStationId = startStationId;
        this.arrivalMillisOfDay = arrivalDateTime.getMillisOfDay();
        this.maxNumberOfResults = maxNumberOfResults;
    }

    @Override
    public Evaluation evaluate(Path path) {
        Node startNode = path.startNode();
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);
        long startNodeArrival = (long) startNode.getProperty(StopNode.ARRIVAL_PROPERTY);

        Node currentNode = path.endNode();
        long currentNodeStationId = (long) currentNode.getProperty(StopNode.STATION_PROPERTY);

        //rozhodujici je departure time pokud existuje, jinak arrival time
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

        Long currentNodeTimeProperty = currentNodeDeparture != null ? currentNodeDeparture : currentNodeArrival;

        //POKUD jsem jiz na teto ceste (od start node) nasel cil v lepsim case
        if(foundedPaths.containsKey(startNodeStopId)) {
            Long prevBestFoundedPathStart = foundedPaths.get(startNodeStopId);
            long currentNodeMillisTimeWithPenalty = currentNodeTimeProperty - (DateTimeUtils.TRANSFER_PENALTY_MILLIS * foundedPathsNumOfTransfers.get(startNodeStopId));
            if(currentNodeMillisTimeWithPenalty < 0) {
                //prehoupl jsem se s penalizaci do dalsiho dne
                currentNodeMillisTimeWithPenalty = DateTimeUtils.MILLIS_IN_DAY + currentNodeMillisTimeWithPenalty;
            }

            if(prevBestFoundedPathStart <= arrivalMillisOfDay) {
                //minuly nejlepsi cil byl ve stejny den jako arrival
                if((currentNodeMillisTimeWithPenalty < prevBestFoundedPathStart && currentNodeMillisTimeWithPenalty <= arrivalMillisOfDay) || currentNodeMillisTimeWithPenalty > arrivalMillisOfDay) {
                    //momentalne jsem taky ve stejny den ale v horsim case nebo jsem az pred pulnoci
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            } else {
                //minuly nejlepsi cil byl pred pulnoci
                if(currentNodeMillisTimeWithPenalty < prevBestFoundedPathStart && currentNodeMillisTimeWithPenalty > arrivalMillisOfDay) {
                    //momentalne jsem taky pred pulnoci ale s horsim casem
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            }
        }

        //vyhledavam na ceste, kde arrivalTime (ciloveho uzlu) je vetsi, nez arrival time nektere jiz nalezene cesty
        //to muzu hned ukoncit, protoze takovy vysledek stejne uzivateli nezobrazim
        if(prevFoundedArrival != null && startNodeArrival > prevFoundedArrival) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //uz jsem nasel pozadovany pocet vysledku
        if(foundedPathsDetails.size() >= maxNumberOfResults) {
            return Evaluation.EXCLUDE_AND_PRUNE;
        }

        //nasel jsem
        if(currentNodeStationId == startStationId) {
            Set<Long> tmpRides = new HashSet<>();
            for(Node n : path.nodes()) {
                tmpRides.add((long) n.getProperty(StopNode.RIDE_PROPERTY));
            }

            long currentNodeMillisTimeWithPenalty = currentNodeTimeProperty - (DateTimeUtils.TRANSFER_PENALTY_MILLIS * tmpRides.size() - 1);
            if(currentNodeMillisTimeWithPenalty < 0) {
                //prehoupl jsem se s penalizaci do predchoziho dne
                currentNodeMillisTimeWithPenalty = DateTimeUtils.MILLIS_IN_DAY + currentNodeMillisTimeWithPenalty;
            }

            boolean saveMe = true;
            List<Long> keysToRemove = new ArrayList<>();
            for (Map.Entry<Long, Set<Long>> entry : foundedPathsDetails.entrySet()) {
                Long key = entry.getKey();
                Set<Long> value = entry.getValue();

                if(!Sets.intersection(tmpRides, value).isEmpty()) {
                    Long pathDeparture = foundedPaths.get(key);
                    if(pathDeparture <= arrivalMillisOfDay) {
                        //cil aktualni cesty byl ve stejny den jako arrival
                        if((currentNodeMillisTimeWithPenalty <= arrivalMillisOfDay && currentNodeMillisTimeWithPenalty < pathDeparture) || currentNodeMillisTimeWithPenalty > arrivalMillisOfDay) {
                            //momentalne jsem v cili taky ve stejny den jako arrival, ale s horsim casem nez jsem jiz byl, nebo jsem v cili az pred pulnoci
                            saveMe = false;
                            break;
                        } else {
                            keysToRemove.add(key);
                        }
                    } else {
                        //cil aktualni cesty byl pred pulnoci
                        if(currentNodeMillisTimeWithPenalty > arrivalMillisOfDay && currentNodeMillisTimeWithPenalty < pathDeparture) {
                            //momentalne jsem taky pred pulnoci ale drive
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

            prevFoundedArrival = startNodeArrival;
            foundedPaths.put(startNodeStopId, currentNodeMillisTimeWithPenalty);
            foundedPathsNumOfTransfers.put(startNodeStopId, tmpRides.size() - 1);
            return Evaluation.INCLUDE_AND_PRUNE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
