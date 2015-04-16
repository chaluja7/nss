package cz.cvut.nss.services;

import cz.cvut.nss.entities.GoogleStop;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleStopService {

    void createGoogleStop(GoogleStop googleStop);

    GoogleStop getGoogleStop(long id);

    void deleteAll();

    List<GoogleStop> getGoogleStopsByTripId(Integer tripId);

    GoogleStop updateGoogleStop(GoogleStop googleStop);

    void createGoogleStop(String stationId, Integer tripId, Integer order, LocalDateTime arrivalTime, LocalDateTime departureTime);

}
