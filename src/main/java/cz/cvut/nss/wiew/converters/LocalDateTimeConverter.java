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
 * Converter for line (prior pro jsf select one)
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class LocalDateTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateTimeUtils.dateTimePattern);
        return formatter.parseLocalDateTime(s);
        //return DateTimeFormat.forPattern(EosDateTimeUtils.dateTimePattern).parseLocalDate(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o == null ? null : ((LocalDateTime)o).toString(DateTimeUtils.dateTimePattern);
    }

}
