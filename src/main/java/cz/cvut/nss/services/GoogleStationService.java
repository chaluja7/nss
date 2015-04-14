package cz.cvut.nss.services;

import cz.cvut.nss.entities.GoogleStation;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleStationService {

    void createGoogleStation(GoogleStation googleStation);

    GoogleStation getGoogleStation(long id);

    void deleteAll();

    GoogleStation getGoogleStationByStationId(String stationId);

    GoogleStation updateGoogleStation(GoogleStation googleStation);

    void createGoogleStation(String stationId, String name, Double latitude, Double longitude, String parentStationId);

    GoogleStation getByName(String name);

}
