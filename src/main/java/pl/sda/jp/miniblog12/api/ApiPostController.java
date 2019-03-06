package pl.sda.jp.miniblog12.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.jp.miniblog12.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api") // wszystkie metody wewnątrz kontrolera będą zaczynać się od tego prefiksu
public class ApiPostController {

    private PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts") // czyli /api/posts
    // tu niepotrzebny jest model, nic nie pośredniczy w komunikacji
    public ResponseEntity<List<PostSummary>> getPosts() {

        List<PostSummary> postSummaryList = postService.getAllPosts().stream().map(post -> new PostSummary(post.getId(), post.getTitle())).collect(Collectors.toList());
        return ResponseEntity.ok(postSummaryList);
    }

}
