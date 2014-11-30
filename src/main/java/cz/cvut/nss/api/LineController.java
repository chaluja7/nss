package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.LineResource;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for route resource.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@RestController
public class LineController {

    @Autowired
    protected LineService lineService;

    @RequestMapping(value ="/lines", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public List<LineResource> getLines() {
        return getAllTransformedLines();
    }

    @RequestMapping(value ="/linesDataTable", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public DataTableResource<LineResource> getLinesForDataTable() {
        return new DataTableResource<>(getAllTransformedLines());
    }

    /**
     * ztransformuje line entity na line resourcy
     *
     * @return list line resourcu
     */
    private List<LineResource> getAllTransformedLines() {
        List<LineResource> resourceList = new ArrayList<>();
        for(Line line : lineService.getAll()) {
            LineResource resource = new LineResource();
            resource.setId(line.getId());
            resource.setName(line.getName());
            resource.setRoute(line.getRoute().getName());
            resource.setType(line.getLineType().name());

            resourceList.add(resource);
        }

        return resourceList;
    }

}
