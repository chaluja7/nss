package cz.cvut.nss.services.afterStart;

import cz.cvut.nss.entities.Ride;
import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.RideService;
import cz.cvut.nss.services.StationService;
import cz.cvut.nss.services.neo4j.StopNeo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 03.04.15
 */
@Service
public class Neo4jImportJob implements ApplicationListener<ContextRefreshedEvent> {

    private static int numberOfInvocation = 0;

    @Autowired
    private StopNeo4jService stopNeo4jService;

    @Autowired
    private StationService stationService;

    @Autowired
    private RideService rideService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(numberOfInvocation++ == 0) {
//            deleteAllStopNodes();
//            importAllStopNodes();
        }
    }

    protected void deleteAllStopNodes() {
        stopNeo4jService.deleteAll();
    }

    protected void importAllStopNodes() {
        //do neo4j vrazim jednotlive RIDES
        List<Ride> rideList = rideService.getAll();
        for(Ride ride : rideList) {
            rideService.importRideToNeo4j(ride.getId());
        }

        //Nyni proiteruji vsechny stanice a propojim prislusne stopy na dane stanici cekacimi hranamy
        for(Station station : stationService.getAll()) {
            stopNeo4jService.connectStopNodesOnStationWithWaitingStopRelationship(station.getId());
        }
    }

}
