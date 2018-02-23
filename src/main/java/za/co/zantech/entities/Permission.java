package za.co.zantech.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by zandrewitte on 2017/06/06.
 * Permission
 */
@Entity
@Table(name = "Permission")
public class Permission {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Size(max = 20)
    private Long id;
    @Size(max = 20)
    private Long roleId;
    @Size(max = 20)
    private Long routeId;

    public Permission(){}

    public Permission(Long id, Long roleId, Long routeId){
        this.id = id;
        this.roleId = roleId;
        this.routeId = routeId;
    }

    public Long getId() {
        return id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getRouteId() {
        return routeId;
    }
}
