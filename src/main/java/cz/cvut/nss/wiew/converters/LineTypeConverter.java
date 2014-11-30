package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.entities.LineType;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter pro region (hlavne pro jsf select one)
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class LineTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return LineType.valueOf(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        LineType lineType = (LineType) o;

        return lineType.name();
    }

}
