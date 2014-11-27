package cz.cvut.nss.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * System user (admin).
 *
 * @author jakubchalupa
 * @since 24.11.14.
 */
@Entity
@Table(name = "persons")
public class Person extends AbstractEntity {

    @Column(unique = true)
    @NotBlank
    private String username;

    @Column
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
