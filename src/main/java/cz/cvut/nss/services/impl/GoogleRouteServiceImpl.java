package cz.cvut.nss.services.impl;

import cz.cvut.nss.dao.GoogleRouteDao;
import cz.cvut.nss.entities.GoogleRoute;
import cz.cvut.nss.services.GoogleRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 15.04.15
 */
@Service
public class GoogleRouteServiceImpl implements GoogleRouteService {

    @Autowired
    protected GoogleRouteDao googleRouteDao;

    @Override
    @Transactional
    public void createGoogleRoute(GoogleRoute googleRoute) {
        googleRouteDao.create(googleRoute);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleRoute getGoogleRoute(long id) {
        return googleRouteDao.find(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        googleRouteDao.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GoogleRoute getGoogleRouteByRouteId(String routeId) {
        return googleRouteDao.findByRouteId(routeId);
    }

    @Override
    @Transactional
    public GoogleRoute updateGoogleRoute(GoogleRoute googleRoute) {
        return googleRouteDao.update(googleRoute);
    }

    @Override
    @Transactional
    public void createGoogleRoute(String routeId, String name, Integer agencyId, Integer routeType) {
        GoogleRoute googleRoute = new GoogleRoute();
        googleRoute.setRouteId(routeId);
        googleRoute.setName(name);
        googleRoute.setAgencyId(agencyId);
        googleRoute.setType(routeType);

        createGoogleRoute(googleRoute);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<GoogleRoute> findAll() {
        return googleRouteDao.findAll();
    }

}
