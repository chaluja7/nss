package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.entities.Region;
import cz.cvut.nss.services.RegionService;
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
public class RegionConverter implements Converter {

    @Autowired
    private RegionService regionService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Long id = Long.valueOf(s);
        return regionService.getRegion(id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Region region = (Region) o;
        Long id = region.getId();
        return String.valueOf(id);
    }

}
