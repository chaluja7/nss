package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

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
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateTimeUtils.DATE_PATTERN);
        return formatter.parseLocalDate(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o == null ? null : ((LocalDate)o).toString(DateTimeUtils.DATE_PATTERN);
    }

}
