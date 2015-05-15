package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.OperationInterval;
import cz.cvut.nss.services.OperationIntervalService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Locale;

/**
 * Backing bean for operation interval.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@ManagedBean
@ViewScoped
public class OperationIntervalBB {

    @ManagedProperty(value = "#{operationIntervalServiceImpl}")
    private OperationIntervalService operationIntervalService;

    private Long id;

    private OperationInterval operationInterval = new OperationInterval();

    public void loadOperationInterval() {
        if(id != null) {
            operationInterval = operationIntervalService.getOperationInterval(id);
        }
    }

    public String saveOperationInterval() {
        operationIntervalService.createOperationInterval(operationInterval);
        return "operationinterval-list?faces-redirect=true";
    }

    public String updateOperationInterval() {
        operationIntervalService.updateOperationInterval(operationInterval);
        return "operationinterval-list?faces-redirect=true";
    }

    public String deleteOperationInterval() {
        operationIntervalService.deleteOperationInterval(operationInterval.getId());
        return "operationinterval-list?faces-redirect=true";
    }

    public Locale getLocale() {
        return new Locale("cs", "CZ");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationInterval getOperationInterval() {
        return operationInterval;
    }

    public void setOperationInterval(OperationInterval operationInterval) {
        this.operationInterval = operationInterval;
    }

    public void setOperationIntervalService(OperationIntervalService operationIntervalService) {
        this.operationIntervalService = operationIntervalService;
    }
}
