package za.co.zantech.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by zandrewitte on 2017/06/06.
 * Route
 */
@Entity
@Table(name = "Route")
public class Route {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Size(max = 20)
    private Long id;
    private String name;
    @Size(max = 6)
    private String method;
    private String description;

    public Route() {}

    public Route(Long id, String name, String method, String description) {
        this.id = id;
        this.name = name;
        this.method = method;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }
}
