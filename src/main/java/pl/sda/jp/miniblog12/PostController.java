package pl.sda.jp.miniblog12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.jp.miniblog12.entity.Comment;
import pl.sda.jp.miniblog12.entity.Post;
import pl.sda.jp.miniblog12.form.NewPostForm;
import pl.sda.jp.miniblog12.service.PostService;
import pl.sda.jp.miniblog12.service.UserContextService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private PostService postService;
    private UserContextService userContextService;


    @Autowired
    public PostController(PostService postService, UserContextService userContextService) {
        this.postService = postService;
        this.userContextService = userContextService;
    }

    @GetMapping("/post/add")
    public String showAddNewPostForm(Model model){
        model.addAttribute("newPostForm", new NewPostForm());
        return "post/addNewPostForm";
    }

    @PostMapping("/post/add")
    public String handleNewPostForm(
            @ModelAttribute @Valid NewPostForm newPostForm, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "post/addNewPostForm";
        }

        postService.addNewPost(newPostForm);

        return "redirect:/"; // przekierowanie przez przeglądarkę
    }

    @GetMapping("/post")
//    public String showSinglePost(@RequestParam Long postId) {
//    jesli przyjelismy inny typ parametru jak niżej możemy zrobic tak:
    public String showSinglePost(@RequestParam String postId, Model model) { // klasa dostarczana przez Springa
        // w przypadku Stringa parsujemy go na longa
        return prepareSinglePost(postId, model);
    }

    // poniżej jest metoda na implementację SEO friendly (dla silnika wyszukiwarek np google'a)
    // i są preferowane w komercyjnych projektach
    // wstawiamy pattern w url; pokazujemy Springowi gdzie i jak nazywa się zmienna
//    @GetMapping("/post/{postId},{postName}")
    @GetMapping("/post/{postId}") // musi być taka sama nazwa jak w PathVariable
    // ewentualnie przy innej nazwie trzeba użyć value w PathVariable i nazwać ją jak zmienną w metodzie
    // PathVariable służy do mapowania URL
    public String showSinglePostByPath(@PathVariable String postId, Model model) { // klasa dostarczana przez Springa
        // w przypadku Stringa parsujemy go na longa
        return prepareSinglePost(postId, model);
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        List<Post> allPosts = postService.getAllPosts();
        model.addAttribute("posts",allPosts);
        return "post/showPosts";
    }

    @PostMapping("/post/{id}/comment/add")
    public String handleNewCommentForm(@PathVariable String id,
                                       @RequestParam String commentBody, //odbieramy w ten sposób parametr requestu
                                       @RequestParam String postId
                                       ) {

        postService.addNewComment(Long.parseLong(id),userContextService.getLoggedAs(),commentBody);
        return "redirect:/post/"+postId;
    }

    private String prepareSinglePost(@PathVariable String postId, Model model) {
        Long postIdLong;
        try {
            postIdLong = Long.parseLong(postId);
        } catch (NumberFormatException e) {
            return "post/postNotFound";
        }

        boolean showCommentForm = userContextService.getLoggedAs() != null && userContextService.hasAllRoles("ROLE_USER","ROLE_ADMIN");
        model.addAttribute("showCommentForm", showCommentForm);

        Optional<Post> postOptional = postService.getSinglePost(Long.valueOf(postId));
        if (postOptional.isPresent() == false) {
            return "post/postNotFound";
        }
        model.addAttribute("post", postOptional.get());
        List<Comment>allComments = postService.getAllComments(postIdLong);
        model.addAttribute("comments",allComments);
        return "post/showSinglePost";
    }

}
