package cz.cvut.nss.services;

import cz.cvut.nss.entities.Region;

import java.util.List;

/**
 * Common interface for all RegionService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface RegionService {

    /**
     * find region by id
     * @param id id of a region
     * @return region by id or null
     */
    Region getRegion(long id);

    /**
     * update region
     * @param region region to update
     * @return updated region
     */
    Region updateRegion(Region region);

    /**
     * persists region
     * @param region region to persist
     */
    void createRegion(Region region);

    /**
     * delete region
     * @param id id of region to delete
     */
    void deleteRegion(long id);

    /**
     * find all regions
     * @return all regions
     */
    List<Region> getAll();

}
