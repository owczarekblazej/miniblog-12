package pl.sda.jp.miniblog12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.jp.miniblog12.config.BasicConfig;
import pl.sda.jp.miniblog12.entity.Role;
import pl.sda.jp.miniblog12.entity.User;
import pl.sda.jp.miniblog12.form.UserRegisterForm;
import pl.sda.jp.miniblog12.repository.RoleRepository;
import pl.sda.jp.miniblog12.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired // nie jest niezbędny ponieważ jest tylko jeden konstruktor
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void registerUser(UserRegisterForm userRegisterForm){
        User user = new User();
        user.setEmail(userRegisterForm.getEmail());
        user.setFirstName(userRegisterForm.getFirstName());
        user.setLastName(userRegisterForm.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisterForm.getPassword()));

        getOrCreateDefaultRole(user);

        userRepository.save(user);
    }
//        Optional<Role> optionalRole = roleRepository.findByRoleName(ROLE_USER);
//        if(!optionalRole.isPresent()) {
//            Role role = new Role(ROLE_USER);
//            roleRepository.save(role);
//        }

    // zamiast powyższego zakomentowanego kodu możemy zrobić tak:
    private void getOrCreateDefaultRole(User user) {
        Role role = roleRepository.findByRoleName(ROLE_USER).orElseGet(() -> roleRepository.save(new Role(ROLE_USER)));
        // orElseGet wykona się tylko wtedy gdy optional był pusty; funkcje są lazy i wykonają się kiedy muszą
        // orElse wykona się zawsze, bo przyjmje wyrażenie javy, które zawsze się wykona
        // supplier to funkcja która przyjmuje void a wraca coś

//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
        user.addRole(role);
    }

}
