package cz.cvut.nss.services;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.utils.dto.EntitiesAndCountResult;

import java.util.List;

/**
 * Common interface for all LineService implementations.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
public interface LineService {

    /**
     * find line by id
     * @param id id of a line
     * @return line by id or null
     */
    public Line getLine(long id);

    /**
     * update line
     * @param line line to update
     * @return updated line
     */
    public Line updateLine(Line line);

    /**
     * persists line
     * @param line line to persist
     */
    public void createLine(Line line);

    /**
     * delete line
     * @param id id of line to delete
     */
    public void deleteLine(long id);

    /**
     * find all lines
     * @return all lines
     */
    public List<Line> getAll();

    /**
     * find all filtered lines for datatables and count
     * @param filter filter
     * @return list lines and count
     */
    public EntitiesAndCountResult<Line> getAllForDatatables(CommonRequest filter);

    /**
     * get count of all lines
     * @return count of all lines
     */
    public int getCountAll();

}
