package cz.cvut.nss.wiew.converters;

import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.services.OperationIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter for operation interval (prior for jsf select one)
 *
 * @author jakubchalupa
 * @since 27.11.14
 */
@Component
public class OperationIntervalConverter implements Converter {

    @Autowired
    private OperationIntervalService operationIntervalService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Long id = Long.valueOf(s);
        return operationIntervalService.getOperationInterval(id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        OperationInterval operationInterval = (OperationInterval) o;
        Long id = operationInterval.getId();
        return String.valueOf(id);
    }

}
