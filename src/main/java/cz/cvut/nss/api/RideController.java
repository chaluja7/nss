package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.RideResource;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.utils.EosDateTimeUtils;
import org.joda.time.Hours;
import org.joda.time.Minutes;
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
public class RideController {

    @Autowired
    protected RideService rideService;

    @RequestMapping(value ="/rides", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public List<RideResource> getRides() {
        return getAllTransformedRides();
    }

    @RequestMapping(value ="/ridesDataTable", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional
    public DataTableResource<RideResource> getRidesForDataTable() {
        return new DataTableResource<>(getAllTransformedRides());
    }

    /**
     * ztransformuje line entity na line resourcy
     *
     * @return list line resourcu
     */
    private List<RideResource> getAllTransformedRides() {
        List<RideResource> resourceList = new ArrayList<>();
        for(Ride ride: rideService.getAll()) {
            RideResource resource = new RideResource();
            resource.setId(ride.getId());
            resource.setLine(ride.getLine().getName());

            List<Stop> stopList = ride.getStops();
            if(stopList.size() > 0) {
                resource.setStationFrom(stopList.get(0).getStation().getTitle());
                resource.setStationTo(stopList.get(stopList.size() - 1).getStation().getTitle());

                Stop from = stopList.get(0);
                Stop to = stopList.get(stopList.size() - 1);

                resource.setDeparture(from.getDeparture().toString(EosDateTimeUtils.dateTimePattern));
                resource.setArrival(to.getArrival().toString(EosDateTimeUtils.dateTimePattern));

                Hours hours = Hours.hoursBetween(from.getDeparture(), to.getArrival());
                Minutes minutes = Minutes.minutesBetween(from.getDeparture(), to.getArrival());
                resource.setDuration(hours.getHours() + ":" + minutes.getMinutes() % 60);

            }

            resourceList.add(resource);
        }

        return resourceList;
    }

}
