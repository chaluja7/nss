package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.entities.Station;
import cz.cvut.nss.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for region (prior pro jsf select one)
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class StationConverter implements Converter {

    @Autowired
    private StationService stationService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Long id = Long.valueOf(s);
        Station station = stationService.getStation(id);

        return station;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Station station = (Station) o;
        Long id = station.getId();

        return String.valueOf(id);
    }

}
