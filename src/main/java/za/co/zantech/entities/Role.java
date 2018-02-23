package za.co.zantech.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by zandrewitte on 2017/06/05.
 * Role
 */
@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Size(max = 20)
    private String name;

    @Size(max = 255)
    private String description;

    public Role() {

    }

    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
