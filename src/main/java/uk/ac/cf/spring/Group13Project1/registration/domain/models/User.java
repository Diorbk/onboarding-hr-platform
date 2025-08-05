package uk.ac.cf.spring.Group13Project1.registration.domain.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class User {

    private boolean enabled;
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private Long roleId;
    private Long assignedId;

    public User(){

    }

    public User(Long id, String username, String password, String email, boolean enabled, Long roleId, Long assignedId)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.roleId = roleId;
        this.assignedId = assignedId;

    }

}