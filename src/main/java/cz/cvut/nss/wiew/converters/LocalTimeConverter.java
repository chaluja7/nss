package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class LocalTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(StringUtils.isEmpty(s)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateTimeUtils.timePattern);
        LocalTime l;
        try {
            l = formatter.parseLocalTime(s);
        } catch (IllegalArgumentException e) {
            l = null;
        }

        return l;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o == null ? null : ((LocalTime)o).toString(DateTimeUtils.timePattern);
    }

}
