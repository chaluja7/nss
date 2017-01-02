package cz.cvut.nss.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Test klonovaci utility kolekci - vyuziva kontroly vsech CRUD operaci kolekce.
 *
 * @author jakubchalupa
 * @since 30.12.16
 */
public class CollectionCloneUtilsTest {

    private static final Long inSetValue = 5L;

    private static final Long notInSetValue = 88L;

    private static final Long inMapKey = 5L;

    private static final Long notInMapKey = 88L;

    private static final Long inMapKeyListValue = 6L;

    private static final Long notInMapKeyListValue = 89L;

    @Test
    public void testCloneSet() throws Exception {
        Set<Long> originalSet = getLongsSet();
        Set<Long> clonedSet = CollectionCloneUtils.cloneSet(originalSet);

        //zkontrolujeme, zda maji sety stejne prvky
        Assert.assertTrue(originalSet.containsAll(clonedSet));
        Assert.assertTrue(clonedSet.containsAll(originalSet));

        //zmeny v cloned setu nesmi ovlivnit originalset
        //odebrani pouze z klonovaneho
        clonedSet.remove(inSetValue);
        Assert.assertFalse(clonedSet.contains(inSetValue));
        Assert.assertTrue(originalSet.contains(inSetValue));

        //pridani pouze do klonovaneho
        clonedSet.add(notInSetValue);
        Assert.assertTrue(clonedSet.contains(notInSetValue));
        Assert.assertFalse(originalSet.contains(notInSetValue));
    }

    @Test
    public void testCloneMap() throws Exception {
        Map<Long, List<Long>> originalMap = getLongsMap();
        Map<Long, List<Long>> clonedMap = CollectionCloneUtils.cloneMap(originalMap);

        //zkontroluju, zda jsou mapy totozne
        for(Map.Entry<Long, List<Long>> entry : originalMap.entrySet()) {
            Assert.assertTrue(clonedMap.containsKey(entry.getKey()));
            Assert.assertTrue(clonedMap.get(entry.getKey()).containsAll(entry.getValue()));
            Assert.assertTrue(entry.getValue().containsAll(clonedMap.get(entry.getKey())));
        }

        for(Map.Entry<Long, List<Long>> entry : clonedMap.entrySet()) {
            Assert.assertTrue(originalMap.containsKey(entry.getKey()));
            Assert.assertTrue(originalMap.get(entry.getKey()).containsAll(entry.getValue()));
            Assert.assertTrue(entry.getValue().containsAll(originalMap.get(entry.getKey())));
        }

        //pridani a odebrani prvku z listu pod znamym klicem
        List<Long> clonedLongs = clonedMap.get(inMapKey);
        clonedLongs.remove(inMapKeyListValue);
        Assert.assertFalse(clonedLongs.contains(inMapKeyListValue));

        List<Long> originalLongs = originalMap.get(inMapKey);
        Assert.assertTrue(originalLongs.contains(inMapKeyListValue));

        clonedLongs.add(notInMapKeyListValue);
        Assert.assertTrue(clonedLongs.contains(notInMapKeyListValue));
        Assert.assertFalse(originalLongs.contains(notInMapKeyListValue));

        //pridani a odebrani klice mapy
        clonedMap.remove(inMapKey);
        Assert.assertFalse(clonedMap.containsKey(inMapKey));
        Assert.assertTrue(originalMap.containsKey(inMapKey));

        clonedMap.put(notInMapKey, new ArrayList<Long>());
        Assert.assertTrue(clonedMap.containsKey(notInMapKey));
        Assert.assertFalse(originalMap.containsKey(notInMapKey));
    }

    private static Set<Long> getLongsSet() {
        Set<Long> set = new HashSet<>();
        set.add(inSetValue);
        set.add(-10L);
        set.add(25L);
        set.add(14L);

        if(set.contains(notInSetValue)) {
            set.remove(notInSetValue);
        }

        return set;
    }

    private static Map<Long, List<Long>> getLongsMap() {
        Map<Long, List<Long>> map = new HashMap<>();

        List<Long> list1 = new ArrayList<>();
        list1.add(inMapKeyListValue);
        list1.add(3L);
        if(list1.contains(notInMapKeyListValue)) {
            list1.remove(notInMapKeyListValue);
        }
        map.put(inMapKey, list1);

        List<Long> list2 = new ArrayList<>();
        list2.add(4L);
        list2.add(5L);
        map.put(1L, list2);

        if(map.containsKey(notInMapKey)) {
            map.remove(notInMapKey);
        }

        return map;
    }

}