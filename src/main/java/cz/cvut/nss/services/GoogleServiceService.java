package cz.cvut.nss.services;

import cz.cvut.nss.entities.GoogleService;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleServiceService {

    void createGoogleService(GoogleService googleService);

    GoogleService getGoogleService(long id);

    void deleteAll();

    GoogleService getGoogleServiceByServiceId(Integer serviceId);

    GoogleService updateGoogleService(GoogleService googleService);

    void createGoogleService(Integer serviceId, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday,
                             Boolean saturday, Boolean sunday, LocalDateTime startDate, LocalDateTime endDate);

    List<GoogleService> getAll();

}
