package cz.cvut.nss.api;

import cz.cvut.nss.api.datatable.DataTableResource;
import cz.cvut.nss.api.datatable.resource.RideResource;
import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Stop;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Transactional
    @RequestMapping(value ="/rides", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<RideResource> getRides() {
        return getTransformedRides(rideService.getAll());
    }

    @Transactional
    @RequestMapping(value ="/ridesDataTable/{lineId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public DataTableResource<RideResource> getRidesForDataTable(@PathVariable Long lineId) {
        if(lineId == null) {
            return new DataTableResource<>();
        }

        return new DataTableResource<>(getTransformedRides(rideService.getByLineId(lineId)));
    }

    /**
     * ztransformuje line entity na line resourcy
     *
     * @return list line resourcu
     */
    private List<RideResource> getTransformedRides(List<Ride> rideList) {
        if(rideList == null) {
            return new ArrayList<>();
        }

        List<RideResource> resourceList = new ArrayList<>();
        for(Ride ride: rideList) {
            RideResource resource = new RideResource();
            resource.setId(ride.getId());
            resource.setLine(ride.getLine().getLineType().name() + " - " + ride.getLine().getName());

            List<Stop> stopList = ride.getStops();
            if(stopList.size() > 0) {
                resource.setStationFrom(stopList.get(0).getStation().getName());
                resource.setStationTo(stopList.get(stopList.size() - 1).getStation().getName());

                Stop from = stopList.get(0);
                Stop to = stopList.get(stopList.size() - 1);

                if(from.getDeparture() != null && to.getArrival() != null) {
                    resource.setDeparture(from.getDeparture().toString(DateTimeUtils.timePattern));
                    resource.setArrival(to.getArrival().toString(DateTimeUtils.timePattern));

                    //todo?
                    Hours hours = Hours.hoursBetween(from.getDeparture(), to.getArrival());

                    //todo?
                    Minutes minutes = Minutes.minutesBetween(from.getDeparture(), to.getArrival());
                    String minutesString = String.valueOf(minutes.getMinutes() % 60);
                    if ((minutes.getMinutes() % 60) < 10) {
                        minutesString = "0" + minutesString;
                    }

                    resource.setDuration(hours.getHours() + ":" + minutesString);
                }

            }

            resourceList.add(resource);
        }

        return resourceList;
    }

}
