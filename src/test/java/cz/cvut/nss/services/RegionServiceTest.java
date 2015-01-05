package cz.cvut.nss.services;

import cz.cvut.nss.entities.Region;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Region service test.
 *
 * @author jakubchalupa
 * @since 05.01.15
 */
public class RegionServiceTest extends AbstractServiceTest {

    @Autowired
    private RegionService regionService;

    @Test
    public void testCreateAndGet() {
        Region region = prepareRegion();

        Region retrieved = regionService.getRegion(region.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(region.getName(), retrieved.getName());
    }

    @Test
    public void testUpdate() {
        Region region = prepareRegion();

        Region retrieved = regionService.getRegion(region.getId());
        Assert.assertNotNull(retrieved);

        retrieved.setName("newName");
        regionService.updateRegion(retrieved);

        Region updated = regionService.getRegion(region.getId());
        Assert.assertNotNull(updated);
        Assert.assertEquals(retrieved.getName(), updated.getName());
    }

    @Test
    public void testDelete() {
        Region region = prepareRegion();

        Region retrieved = regionService.getRegion(region.getId());
        Assert.assertNotNull(retrieved);

        regionService.deleteRegion(region.getId());
        Assert.assertNull(regionService.getRegion(region.getId()));
    }

    private Region prepareRegion() {
        Region region = new Region();
        region.setName("testName");

        regionService.createRegion(region);
        return region;
    }

}
