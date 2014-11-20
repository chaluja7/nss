package cz.cvut.nss.services;

import cz.cvut.nss.entities.Ride;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Common interface for all RideService implementations.
 */
public interface RideService {

    /**
     * find ride by id
     * @param id id of a ride
     * @return ride by id or null
     */
    public Ride getRide(long id);

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

}
