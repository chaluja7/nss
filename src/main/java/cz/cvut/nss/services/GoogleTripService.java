package cz.cvut.nss.services;

import cz.cvut.nss.entities.GoogleTrip;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleTripService {

    void createGoogleTrip(GoogleTrip googleTrip);

    GoogleTrip getGoogleTrip(long id);

    void deleteAll();

    GoogleTrip getGoogleTripByTripId(Integer tripId);

    GoogleTrip updateGoogleTrip(GoogleTrip googleTrip);

    void createGoogleTrip(Integer tripId, String routeId, Integer serviceId);

    GoogleTrip getOneByRouteId(String routeId);

}
