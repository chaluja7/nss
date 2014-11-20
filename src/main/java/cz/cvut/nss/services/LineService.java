package cz.cvut.nss.services;

import cz.cvut.nss.entities.Line;

import java.util.List;

/**
 * Created by jakubchalupa on 20.11.14.
 *
 * Common interface for all LineService implementations.
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

}
