package cz.cvut.nss.utils.evaluator;

import com.google.common.collect.Sets;
import cz.cvut.nss.entities.neo4j.StopNode;
import org.joda.time.LocalDateTime;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Evaluator for neo4j traversal (finding paths from end station)
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public final class ArrivalTypeEvaluator implements Evaluator {

    private final Long endStationId;

    private final int departureMillisOfDay;

    private final int maxNumberOfResults;

    Map<Long, Long> foundedPaths = new HashMap<>();

    Map<Long, Set<Long>> foundedPathsDetails = new HashMap<>();

    public ArrivalTypeEvaluator(Long endStationId, LocalDateTime departureDateTime, int maxNumberOfResults) {
        this.endStationId = endStationId;
        this.departureMillisOfDay = departureDateTime.getMillisOfDay();
        this.maxNumberOfResults = maxNumberOfResults;
    }

    @Override
    public Evaluation evaluate(Path path) {
        Node startNode = path.startNode();
        long startNodeStopId = (long) startNode.getProperty(StopNode.STOP_PROPERTY);

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
            if(prevBestFoundedPathStart >= departureMillisOfDay) {
                //minuly nejlepsi cil byl pred pulnoci
                if((currentNodeTimeProperty > prevBestFoundedPathStart && currentNodeTimeProperty >= departureMillisOfDay) || currentNodeTimeProperty < departureMillisOfDay) {
                    //momentalne jsem taky pred pulnoci ale v horsim case nebo jsem az po pulnoci
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            } else {
                //minuly nejlepsi cil byl po pulnoci
                if(currentNodeTimeProperty > prevBestFoundedPathStart && currentNodeTimeProperty < departureMillisOfDay) {
                    //momentalne jsem taky po pulnoci ale s horsim casem
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            }
        }

        int i = 0;
        if(foundedPathsDetails.size() >= maxNumberOfResults) {
            for(Long key : foundedPathsDetails.keySet()) {
                Long actualArrival = foundedPaths.get(key);
                if(actualArrival >= departureMillisOfDay) {
                    //tento prijezd byl pred pulnoci
                    if((currentNodeTimeProperty >= departureMillisOfDay && actualArrival < currentNodeTimeProperty) || currentNodeTimeProperty < departureMillisOfDay) {
                        //aktualne jsem taky pred pulnoci ale pozdeji nebo jsem az po pulnoci
                        i++;
                    }
                } else {
                    //tento prijezd byl po pulnoci
                    if(currentNodeTimeProperty < departureMillisOfDay && currentNodeTimeProperty > actualArrival) {
                        //momentalne jsem taky po pulnoci a s horsim casem
                        i++;
                    }
                }

                if(i >= maxNumberOfResults) {
                    //Jiz jsem nasel maxNumberOfResults vice lepsich vysledku
                    return Evaluation.EXCLUDE_AND_PRUNE;
                }
            }
        }

        //nasel jsem
        if(currentNodeStationId == endStationId) {
            Set<Long> tmpRides = new HashSet<>();
            for(Node n : path.nodes()) {
                tmpRides.add((long) n.getProperty(StopNode.RIDE_PROPERTY));
            }

            boolean saveMe = true;
            for (Map.Entry<Long, Set<Long>> entry : foundedPathsDetails.entrySet()) {
                Long key = entry.getKey();
                Set<Long> value = entry.getValue();

                if(!Sets.intersection(tmpRides, value).isEmpty()) {
                    Long pathArrival = foundedPaths.get(key);
                    if(pathArrival >= departureMillisOfDay) {
                        //cil aktualni cesty byl pred pulnoci
                        if((currentNodeTimeProperty >= departureMillisOfDay && currentNodeTimeProperty > pathArrival) || currentNodeTimeProperty < departureMillisOfDay) {
                            //momentalne jsem v cili taky pred pulnoci, ale s horsim casem nez jsem jiz byl, nebo jsem v cili az po pulnoci
                            saveMe = false;
                            break;
                        } else {
                            foundedPathsDetails.remove(key);
                            break;
                        }
                    } else {
                        //cil aktualni cesty byl po pulnoci
                        if(currentNodeTimeProperty < departureMillisOfDay && currentNodeTimeProperty > pathArrival) {
                            //momentalne jsem taky po pulnoci ale pozdeji
                            saveMe = false;
                            break;
                        } else {
                            foundedPathsDetails.remove(key);
                            break;
                        }
                    }
                }
            }

            if(saveMe) {
                foundedPathsDetails.put(startNodeStopId, tmpRides);
            }

            foundedPaths.put(startNodeStopId, currentNodeTimeProperty);
            return Evaluation.INCLUDE_AND_PRUNE;
        }

        return Evaluation.EXCLUDE_AND_CONTINUE;
    }

}
