package cz.cvut.nss.wiew.Validators;

import cz.cvut.nss.utils.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;

/**
 * Local time validator.
 *
 * @author jakubchalupa
 * @since 06.05.15
 */
@Component
public class LocalTimeValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o == null || StringUtils.isEmpty(o.toString())) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateTimeUtils.TIME_PATTERN);
        DateTimeFormatter formatterWithMillis = DateTimeFormat.forPattern(DateTimeUtils.TIME_WITH_MILLIS_PATTERN);
        try {
            formatter.parseLocalTime(o.toString());
        } catch (IllegalArgumentException e) {
            try {
                formatterWithMillis.parseLocalTime(o.toString());
            } catch (IllegalArgumentException ex) {
                throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("messages").getString("validator.time.invalid")));
            }
        }
    }

}
