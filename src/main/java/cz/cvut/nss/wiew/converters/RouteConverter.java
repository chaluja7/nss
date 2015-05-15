package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.entities.Route;
import cz.cvut.nss.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for route (prior pro jsf select one)
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class RouteConverter implements Converter {

    @Autowired
    private RouteService routeService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Long id = Long.valueOf(s);
        return routeService.getRoute(id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Route route = (Route) o;
        Long id = route.getId();
        return String.valueOf(id);
    }

}
