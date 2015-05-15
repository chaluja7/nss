package cz.cvut.nss.dao;

import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.Ride;

import java.util.List;

/**
 * Interface for all implementations of RideDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface RideDao extends GenericDao<Ride> {

    List<Ride> getByLineId(long lineId);

}
