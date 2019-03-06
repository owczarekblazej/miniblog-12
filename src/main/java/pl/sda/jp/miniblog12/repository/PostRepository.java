package pl.sda.jp.miniblog12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jp.miniblog12.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
