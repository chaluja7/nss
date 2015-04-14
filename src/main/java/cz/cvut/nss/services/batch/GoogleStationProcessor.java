package cz.cvut.nss.services.batch;

import cz.cvut.nss.entities.GoogleStation;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public class GoogleStationProcessor implements ItemProcessor<Object, GoogleStation> {

    @Override
    public GoogleStation process(Object o) throws Exception {
        GoogleStation googleStation = new GoogleStation();
        googleStation.setName("adf");
        googleStation.setLatitude(20.0);
        googleStation.setLongtitude(40.0);
        googleStation.setStationId("alsjdf");

        return googleStation;
    }
}
