package pl.sda.jp.miniblog12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.jp.miniblog12.form.UserRegisterForm;
import pl.sda.jp.miniblog12.service.UserService;
import pl.sda.jp.miniblog12.service.UserSessionService;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;
    private UserSessionService userSessionService;

    @Autowired
    public UserController(UserService userService, UserSessionService userSessionService) {
        this.userService = userService;
        this.userSessionService = userSessionService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("userRegisterForm",new UserRegisterForm());
        return "user/registerForm";
    }

    @PostMapping("/register")
    public String handleRegisterForm(
            @ModelAttribute @Valid UserRegisterForm userRegisterForm,
            BindingResult bindingResult
            ){
        // adnotacja Model Attribute zastępuje RequestParam - ważne żeby pola miały tą samą nazwę co zmienne parametrów
        // dodaje obiekt do modelu i możemy się nim posługiwać na HTML'u
        // adnotacja Valid mówi springowi żeby zwalidował parametry wg reguł z adnotacji z klasy userRegisterForm
        // bez tego adnotacje nad jego polami by nie zadziałały
        // binding result musi być za walidowanym polem - otrzymuje on wynik walidacji pola
        if(bindingResult.hasErrors()){
            return "user/registerForm";
        }
        //System.out.println(firstName + ", " + lastName + ", " + email + ", " + password);
        //System.out.println(userRegisterForm);

        userService.registerUser(userRegisterForm);

        return "redirect:/home";
        // Ważne!! przy formularzach POST używamy takiego przekierowania na URL, a nie widok HTML
        // żeby np. nie rejestrować użytkownika z powrotem
    }


    // dodaliśmy formularz wykorzystujący spring security
    @GetMapping("/login-by-spring")
    public String showLoginFormBySpringSecurity(){
        return "user/loginFormBySpring";
    }

    // to poniższe logowanie jest już niepotrzebne jeśli używamy spring security
    @GetMapping("/loginForm")
    public String showLoginForm(){
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String handleLoginForm(@RequestParam String username,
                                  @RequestParam String password){

        boolean userLogged = userSessionService.loginUser(username, password);

        if(!userLogged){
            return "user/loginForm";
        }
        return "redirect:/";
    }

    @GetMapping("/logoutForm")
    public String logoutForm(){
        userSessionService.logout();
        return "user/logoutForm";
    }
}
