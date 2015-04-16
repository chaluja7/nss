package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.GoogleServiceDao;
import cz.cvut.nss.entities.GoogleService;
import cz.cvut.nss.services.GoogleServiceService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jakubchalupa
 * @since 15.04.15
 */
@Service
public class GoogleServiceServiceImpl implements GoogleServiceService {

    @Autowired
    protected GoogleServiceDao googleServiceDao;

    @Override
    @Transactional
    public void createGoogleService(GoogleService googleService) {
        googleServiceDao.create(googleService);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleService getGoogleService(long id) {
        return googleServiceDao.find(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        googleServiceDao.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleService getGoogleServiceByServiceId(Integer serviceId) {
        return googleServiceDao.findByServiceId(serviceId);
    }

    @Override
    @Transactional
    public GoogleService updateGoogleService(GoogleService googleService) {
        return googleServiceDao.update(googleService);
    }

    @Override
    @Transactional
    public void createGoogleService(Integer serviceId, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday,
                                    Boolean friday, Boolean saturday, Boolean sunday, LocalDateTime startDate, LocalDateTime endDate) {

        GoogleService googleService = new GoogleService();
        googleService.setServiceId(serviceId);
        googleService.setMonday(monday);
        googleService.setTuesday(tuesday);
        googleService.setWednesday(wednesday);
        googleService.setThursday(thursday);
        googleService.setFriday(friday);
        googleService.setSaturday(saturday);
        googleService.setSunday(sunday);
        googleService.setStartDate(startDate);
        googleService.setEndDate(endDate);

        createGoogleService(googleService);
    }
}
