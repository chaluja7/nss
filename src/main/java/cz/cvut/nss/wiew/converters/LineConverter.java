package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for line (prior for jsf select one)
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class LineConverter implements Converter {

    @Autowired
    private LineService lineService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Long id = Long.valueOf(s);
        Line line = lineService.getLine(id);

        return line;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Line line = (Line) o;
        Long id = line.getId();

        return String.valueOf(id);
    }

}
