package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.GoogleService;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public interface GoogleServiceDao extends GenericDao<GoogleService> {

    void deleteAll();

    GoogleService findByServiceId(Integer serviceId);

}
