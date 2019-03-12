package pl.sda.jp.miniblog12.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserEditForm {
    @NotNull(message = "Pole imię musi być wypełnione.")
    private String firstName;
    @NotBlank(message = "Pole nazwisko nie może być puste.")
    private String lastName;

}
