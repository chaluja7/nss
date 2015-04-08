package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.CommonRequest;
import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.LineResource;
import cz.cvut.nss.entities.Line;
import cz.cvut.nss.services.LineService;
import cz.cvut.nss.utils.dto.EntitiesAndCountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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
        return getTransformedLines(lineService.getAll());
    }

    @RequestMapping(value ="/linesDataTable", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @Transactional
    public DataTableResource<LineResource> getLinesForDataTable(@RequestBody CommonRequest filter) {
        EntitiesAndCountResult<Line> allForDatatables = lineService.getAllForDatatables(filter);
        return new DataTableResource<>(getTransformedLines(allForDatatables.getEntities()), allForDatatables.getCount(), lineService.getCountAll());
    }

    /**
     * ztransformuje line entity na line resourcy
     *
     * @return list line resourcu
     */
    private List<LineResource> getTransformedLines(List<Line> lineList) {
        List<LineResource> resourceList = new ArrayList<>();
        for(Line line : lineList) {
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
