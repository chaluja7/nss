package cz.cvut.nss.utils.evaluator;

import org.neo4j.graphdb.traversal.TraversalBranch;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * @author jakubchalupa
 * @since 01.05.15
 */
public class SuperPriorityQueue {

    TreeMap<Long, TreeMap<Long, LinkedList<TraversalBranch>>> paths = new TreeMap<>();

    public void addPath(TraversalBranch path, long nodeTime, long travelTime) {
        if(paths.containsKey(nodeTime)) {
            TreeMap<Long, LinkedList<TraversalBranch>> map = paths.get(nodeTime);
            if(map.containsKey(travelTime)) {
                LinkedList<TraversalBranch> list = map.get(travelTime);
                list.add(path);
            } else {
                LinkedList<TraversalBranch> list = new LinkedList<>();
                list.add(path);
                map.put(travelTime, list);
            }
        } else {
            TreeMap<Long, LinkedList<TraversalBranch>> map = new TreeMap<>();
            LinkedList<TraversalBranch> list = new LinkedList<>();
            list.add(path);
            map.put(travelTime, list);
            paths.put(nodeTime, map);
        }
    }

    public TraversalBranch poll() {
        if(paths.isEmpty()) {
            return null;
        }

        Long firstKey = paths.firstKey();
        TreeMap<Long, LinkedList<TraversalBranch>> results = paths.get(firstKey);
        Long firstKeyResults = results.firstKey();
        LinkedList<TraversalBranch> traversalBranchList = results.get(firstKeyResults);
        TraversalBranch traversalBranch = traversalBranchList.poll();

        if(traversalBranchList.isEmpty()) {
            results.remove(firstKeyResults);

            if(results.isEmpty()) {
                paths.remove(firstKey);
            }
        }

        return traversalBranch;
    }

}
