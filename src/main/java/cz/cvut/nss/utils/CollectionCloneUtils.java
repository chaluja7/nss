package cz.cvut.nss.utils;

import java.util.*;

/**
 * Util for deep collections cloning.
 *
 * @author jakubchalupa
 * @since 02.05.15
 */
public class CollectionCloneUtils {

    public static Set<Long> cloneSet(Set<Long> set) {
        Set<Long> clone = new HashSet<>(set.size());
        for(Long item : set) {
            clone.add(item);
        }

        return clone;
    }

    public static Map<Long, List<Long>> cloneMap(Map<Long, List<Long>> map) {
        Map<Long, List<Long>> clone = new HashMap<>(map.size());
        for (Map.Entry<Long, List<Long>> entry : map.entrySet()) {
            List<Long> cloneList = new ArrayList<>();
            for(Long item : entry.getValue()) {
                cloneList.add(item);
            }

            clone.put(entry.getKey(), cloneList);
        }

        return clone;
    }

}
