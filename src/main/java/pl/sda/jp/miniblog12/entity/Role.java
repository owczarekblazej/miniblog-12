package pl.sda.jp.miniblog12.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_role",length = 30, unique = true)
    private String roleName;

    // w bazie danych konwencja jest taka że role wstawiamy poprzez ROLE_nazwaRoli


    public Role(String roleName) {
        this.roleName = roleName;
    }
}
