package cz.cvut.nss.services.impl;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Implementation of SearchService.
 *
 * @author jakubchalupa
 * @since 06.12.14
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    protected SearchDao searchDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<SearchResultWrapper> findPath(long stationFromId, long stationToId, Date departure, int maxDays) {
        return searchDao.findRides(stationFromId, stationToId, departure, maxDays);
    }

    @Override
    public List<SearchResultWrapper> findPathNew(long stationFromId, long stationToId, Date departure, int maxDays) {
        return searchDao.findRidesNew(stationFromId, stationToId, departure, maxDays);
    }

}