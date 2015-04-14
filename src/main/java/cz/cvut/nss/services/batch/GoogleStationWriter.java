package cz.cvut.nss.services.batch;

import cz.cvut.nss.entities.GoogleStation;
import cz.cvut.nss.services.GoogleStationService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public class GoogleStationWriter implements ItemWriter<Object> {

    @Autowired
    private GoogleStationService googleStationService;

    @Override
    public void write(List<?> objects) throws Exception {
        for(Object item : objects) {
            googleStationService.createGoogleStation((GoogleStation) item);
        }
    }
}
