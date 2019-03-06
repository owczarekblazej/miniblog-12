package pl.sda.jp.miniblog12.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jp.miniblog12.entity.Comment;
import pl.sda.jp.miniblog12.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByPost(Post post);
}
