package cz.cvut.nss.wiew;

import cz.cvut.nss.entities.Line;
import cz.cvut.nss.entities.LineType;
import cz.cvut.nss.entities.Route;
import cz.cvut.nss.services.LineService;
import cz.cvut.nss.services.RouteService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Backing bean for line.
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@ManagedBean
@ViewScoped
public class LineBB {

    @ManagedProperty(value = "#{lineServiceImpl}")
    private LineService lineService;

    @ManagedProperty(value = "#{routeServiceImpl}")
    private RouteService routeService;

    private Long id;

    private Line line = new Line();

    public void loadLine() {
        if(id != null) {
            line = lineService.getLine(id);
        }
    }

    public String saveLine() {
        lineService.createLine(line);
        return "line-list?faces-redirect=true";
    }

    public String updateLine() {
        lineService.updateLine(line);
        return "line-list?faces-redirect=true";
    }

    public String deleteLine() {
        lineService.deleteLine(line.getId());
        return "line-list?faces-redirect=true";
    }

    public Map<String, Object> getAllRoutes() {
        Map<String, Object> map = new HashMap<>();
        for(Route route : routeService.getAll()) {
            map.put(route.getName(), route);
        }

        return map;
    }

    public Map<String, Object> getAllLineTypes() {
        Map<String, Object> map = new HashMap<>();
        for(LineType lineType : LineType.values()) {
            map.put(lineType.name(), lineType);
        }

        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setLineService(LineService lineService) {
        this.lineService = lineService;
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

}
