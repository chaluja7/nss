package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for local date time.
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class LocalDateTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateTimeUtils.DATE_TIME_PATTERN);
        return formatter.parseLocalDateTime(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o == null ? null : ((LocalDateTime)o).toString(DateTimeUtils.DATE_TIME_PATTERN);
    }

}
