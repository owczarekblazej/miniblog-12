package pl.sda.jp.miniblog12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.jp.miniblog12.service.CurrentDateTimeProvider;
import pl.sda.jp.miniblog12.service.CurrentDateTimeService;
import pl.sda.jp.miniblog12.service.MessageService;
import pl.sda.jp.miniblog12.service.UserSessionService;

import java.util.Set;

//@Component
@Controller
public class MainController {

    @Autowired
    private MessageService currentDateTimeService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private Set<MessageProvider> messageProvider;

    @RequestMapping(value = {"/", "/home"}) // jeden mapping jedna metoda
    public String home(Model model){
        model.addAttribute("userLogged", userSessionService.isLogged());
        messageProvider.forEach(mp -> System.out.println(mp.getMessage()));
        model.addAttribute("date", currentDateTimeService.getMessage());
        return "homePage";
    }

    //@RequestMapping(name = "/hello", method = RequestMethod.GET)
    @GetMapping("/hello")
    public String hello(Model model, @RequestParam(required = false) String nameParam){
        if(nameParam != null){
            model.addAttribute("name", nameParam);
        } else {
            model.addAttribute("name", "Anonymous");
        }
        model.addAttribute("date", currentDateTimeService.getMessage());
        return "hello";
    }

    //@RequestMapping( name = "/params", method =RequestMethod.GET) jest to to samo co niżej
    @RequestMapping("/params")
    public String params(@RequestParam(required = false, name = "test") String testParam){ // możemy zmienić nazwe parametru - domyślnie parametr ma nazwę zmiennej
        System.out.println("Param test: " + testParam);
        return "params";
    }

}
