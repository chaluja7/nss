package cz.cvut.nss.services;

import cz.cvut.nss.entities.Ride;

import java.util.List;

/**
 * Common interface for all RideService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface RideService {

    /**
     * find ride by id
     * @param id id of a ride
     * @return ride by id or null
     */
    public Ride getRide(long id);

    /**
     * find ride by id with pre-loaded lazy initialization
     * @param id id of a ride
     * @return ride by id or null
     */
    public Ride getRideLazyInitialized(long id);

    /**
     * update ride
     * @param ride ride to update
     * @return updated ride
     */
    public Ride updateRide(Ride ride);

    /**
     * persists ride
     * @param ride ride to persist
     */
    public void createRide(Ride ride);

    /**
     * delete ride
     * @param id id of ride to delete
     */
    public void deleteRide(long id);

    /**
     * find all rides
     * @return all rides
     */
    public List<Ride> getAll();

    /**
     * import the whole ride into neo4j graph db
     * @param id ride id
     */
    public void importRideToNeo4j(Ride id);

}
