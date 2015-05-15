package cz.cvut.nss.dao;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.dao.generics.GenericDao;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.utils.dto.IdsAndCountResult;

import java.util.List;

/**
 * Interface for all implementations of LineDao.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface LineDao extends GenericDao<Line> {

    IdsAndCountResult getLineIdsByFilter(CommonRequest filter);

    /**
     * find lines by id
     * @param lineIds line ids
     * @param preserveOrder if order should be as the one from list
     * @return list lines
     */
    List<Line> getByIds(List<Long> lineIds, boolean preserveOrder);

    /**
     * get count of all lines
     * @return count of all lines
     */
    int getCountAll();

}
