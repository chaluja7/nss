package cz.cvut.nss.entities.neo4j;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Node stanice v grafu.
 *
 * @author jakubchalupa
 * @since 15.03.15
 */
@NodeEntity
public class StationNode {

    @GraphId
    private Long id;

    @Indexed
    @GraphProperty
    private Long stationId;

    @GraphProperty
    private String title;

    @GraphProperty
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
