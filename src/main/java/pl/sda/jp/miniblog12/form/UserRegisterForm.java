package pl.sda.jp.miniblog12.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString(exclude = {"password"}) // wyłączamy z toStringa pole password
public class UserRegisterForm {
    @NotNull(message = "Pole imię musi byc wypełnione.")
    private String firstName;
    @NotBlank(message = "Pole nazwisko nie może być puste.")
    private String lastName;
    @Email
    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}",
            message = "Pole email jest niepoprawne.")
    private String email;
    @Size(min=5, max=25, message = "Hasło musi mieć pomiędzy {min} a {max} znaków")
    private String password;
}
