package pl.sda.jp.miniblog12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BasicConfig {

    // ze wzgledu na unikanie słówka "new" (new BCryptPasswordEncoder()) tworzymy nową metodę
    // musi się taka metoda z adnotacją @Bean w klasie z adnotacją @Configuration
    // to co zwraca ta metoda jest bean'em
    // przenieśliśmy to do innej klasy bo jakiś błąd wywalało ;-)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
