package pl.sda.jp.miniblog12.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // dodane tak dla pewności ;-)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
    //Ctrl+O pokazuje panel ze wszystkimi metodami które można przeładować

    @Autowired
    private BasicConfig basicConfig;

    @Autowired // tutaj wystarczy wstrzykniecie przez pole
    // Spring używa pod spodem JPA a zatem JDBC a zatem datasource ;-)
    private DataSource dataSource;

    // podajemy do jakich stron mają dostęp konkretne role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/post/add").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/post/*/comment/add").hasAnyRole("USER","ADMIN")
                .anyRequest().permitAll()
                // teraz robimy kolejne rzeczy
                .and()
                .csrf().disable()
                .headers().frameOptions().disable() // bez tego nie działałaby konsola H2
                .and()
                .formLogin()
                .loginPage("/login-by-spring")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login-by-spring?status=error")// niepoprawne logowanie przesyła nas na strone
                .loginProcessingUrl("login-post-by-spring") // podajemy to URL na ktory przesyłamy submit
                .defaultSuccessUrl("/hello"); // poprawne logowanie przesyła nas na strone

    }

    // Authority to: ROLE_USER ; authority to ciąg znaków przed rolami wraz z rolą
    // Role t: USER

    // spring security potrzebuje listy ról aby autentykować użytkownika:
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                // spring security zwraca 1 login, 2 haslo i 3 flaga czy ktos jest aktywny, a przyjmuje jeden parametr wejsciowy
                // w postaci loginu
                .usersByUsernameQuery("SELECT u.email, u.password_hash, 1 FROM user u WHERE u.email = ? ")
                // select gdzie 1 login i 2 rola
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name " +
                        "FROM user u " +
                        "JOIN user_role ur ON u.id = ur.user_id " +
                        "JOIN role r ON ur.roles_id = r.id " +
                        "WHERE u.email = ?"
                )
                // podajemy springowi jak odhashować hasło
                .passwordEncoder(basicConfig.passwordEncoder());
    }



}
