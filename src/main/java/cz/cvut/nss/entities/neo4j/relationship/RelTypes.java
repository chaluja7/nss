package cz.cvut.nss.entities.neo4j.relationship;

import org.neo4j.graphdb.RelationshipType;

/**
 * Mozne typy relaci v grafu.
 *
 * @author jakubchalupa
 * @since 28.03.15
 */
public enum RelTypes implements RelationshipType {

    NEXT_STOP,
    NEXT_AWAITING_STOP,
    IN_RIDE,
    IN_INTERVAL

}
