package pl.sda.jp.miniblog12.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name="email", length = 150, unique = true)
    private String email;
    @Column(name = "passwordHash", length = 100)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_role")
    private Set<Role> roles;

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

}
