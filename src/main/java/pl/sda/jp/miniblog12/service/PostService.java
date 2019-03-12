package pl.sda.jp.miniblog12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.jp.miniblog12.entity.Comment;
import pl.sda.jp.miniblog12.entity.Post;
import pl.sda.jp.miniblog12.entity.User;
import pl.sda.jp.miniblog12.form.NewPostForm;
import pl.sda.jp.miniblog12.repository.CommentRepository;
import pl.sda.jp.miniblog12.repository.PostRepository;
import pl.sda.jp.miniblog12.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public void addNewPost(NewPostForm newPostForm) {
        Post post = new Post();
        post.setTitle(newPostForm.getTitle());
        post.setPostBody(newPostForm.getPostBody());
        post.setAdded(LocalDateTime.now());

        postRepository.save(post);
    }

    public Optional<Post> getSinglePost(Long postId) { // najlepiej używać optionala
        return postRepository.findById(postId);
        // ewentualnie może zwracać klasę dającą nulla ale trzeba użyć metody Optional.ofNullable,
        // aby przekonwertować na optionala
//        Post one = postRepository.getOne(postId);
//        return Optional.ofNullable(one);
//        if(one == null){
//            return Optional.empty();
//        } else {
//            return Optional.of(one);
//        }

    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void addNewComment(Long postId, String userLogin, String commentBody) {
        Comment comment = new Comment();
        comment.setCommentBody(commentBody);
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found"));
        User user = userRepository.findByEmail(userLogin).orElseThrow(() -> new RuntimeException("user not found"));

        comment.setPost(post);
        comment.setUser(user);
        comment.setAdded(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findAllByPost(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found")));
    }
}
