package cz.cvut.nss.utils.traversal;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author jakubchalupa
 * @since 30.12.16
 */
public class StationRideWrapperTest {

    @Test
    public void testGetVisitedRides() throws Exception {
        StationRideWrapper wrapper = new StationRideWrapper();
        Assert.assertNotNull(wrapper.getVisitedRides());
        Assert.assertTrue(wrapper.getVisitedRides().isEmpty());

        Set<Long> set = new HashSet<>();
        set.add(5L);
        wrapper.setVisitedRides(set);

        Assert.assertNotNull(wrapper.getVisitedRides());
        Assert.assertEquals(1, wrapper.getVisitedRides().size());
    }

    @Test
    public void testGetVisitedStations() throws Exception {
        StationRideWrapper wrapper = new StationRideWrapper();
        Assert.assertNotNull(wrapper.getVisitedStations());
        Assert.assertTrue(wrapper.getVisitedStations().isEmpty());


        List<Long> list = new ArrayList<>();
        list.add(5L);

        Map<Long, List<Long>> map = new HashMap<>();
        map.put(1L, list);
        wrapper.setVisitedStations(map);

        Assert.assertNotNull(wrapper.getVisitedStations());
        Assert.assertEquals(1, wrapper.getVisitedStations().size());
    }

}