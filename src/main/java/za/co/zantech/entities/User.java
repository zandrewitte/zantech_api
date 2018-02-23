package za.co.zantech.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by zandrewitte on 2017/05/31.
 * User
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Size(max = 20)
    private String userName;

    @Size(max = 40)
    private String password;

    @Size(max = 10)
    private String status;

    @Size(max = 20)
    private Long roleId;

    public User() {

    }

    public User(Long id, String userName, String password, String status, Long roleId) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public Long getRoleId() {
        return roleId;
    }
}
