package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.OperationIntervalResource;
import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.services.OperationIntervalService;
import cz.cvut.nss.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OperationIntervalController {

    @Autowired
    protected OperationIntervalService operationIntervalService;

    @RequestMapping(value ="/operationIntervals", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<OperationIntervalResource> getOperationIntervals() {
        return getTransformedOperationIntervals(operationIntervalService.getAll());
    }

    @RequestMapping(value ="/operationIntervalsDataTable", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public DataTableResource<OperationIntervalResource> getOperationIntervalsForDataTable() {
        return new DataTableResource<>(getTransformedOperationIntervals(operationIntervalService.getAll()));
    }

    private List<OperationIntervalResource> getTransformedOperationIntervals(List<OperationInterval> operationIntervals) {
        List<OperationIntervalResource> resourceList = new ArrayList<>();
        for(OperationInterval operationInterval : operationIntervals) {
            OperationIntervalResource resource = new OperationIntervalResource();

            resource.setId(operationInterval.getId());
            resource.setMonday(operationInterval.getMonday());
            resource.setTuesday(operationInterval.getTuesday());
            resource.setWednesday(operationInterval.getWednesday());
            resource.setThursday(operationInterval.getThursday());
            resource.setFriday(operationInterval.getFriday());
            resource.setSaturday(operationInterval.getSaturday());
            resource.setSunday(operationInterval.getSunday());
            resource.setStartDate(operationInterval.getStartDate().toString(DateTimeUtils.datePattern));
            resource.setEndDate(operationInterval.getEndDate().toString(DateTimeUtils.datePattern));

            resourceList.add(resource);
        }

        return resourceList;
    }

}
