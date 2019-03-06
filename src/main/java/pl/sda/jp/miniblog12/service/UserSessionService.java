package pl.sda.jp.miniblog12.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.sda.jp.miniblog12.entity.User;
import pl.sda.jp.miniblog12.repository.UserRepository;

import java.util.Optional;

@Service
//@Scope(value = "session")
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Getter
    private boolean logged;

    @Getter
    private User user;

    @Autowired
    public UserSessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean loginUser(String username, String password){
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(!userOptional.isPresent()){
            this.logged = false;
            return false;
        }
// po staremu:
//        if(userOptional.get().getPassword().equals(password)){
//            this.logged = true;
//            this.user = userOptional.get();
//            return true;
//        }

        // b-crypt:
        if(passwordEncoder.matches(password,userOptional.get().getPassword())){
            this.logged = true;
            this.user = userOptional.get();
            return true;
        }

        this.logged = false;
        return false;
    }

    public void logout(){
        this.logged = false;
        this.user = null;
    }


}
