package pl.sda.jp.miniblog12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jp.miniblog12.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    List<User>  // zero lub więcej niż jeden wynik

    // zero lub 1 wynik:
    Optional<User> findByEmail(String email);
}
