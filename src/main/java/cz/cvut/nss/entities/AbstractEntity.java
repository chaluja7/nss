package cz.cvut.nss.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jakubchalupa on 19.11.14.
 *
 * Super class for all entities.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof AbstractEntity) {
            AbstractEntity other = (AbstractEntity) obj;
            if(this.id == null || other.id == null) {
                return false;
            } else {
                return this.id.equals(other.id);
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
