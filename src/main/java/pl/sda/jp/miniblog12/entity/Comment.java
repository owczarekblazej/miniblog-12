package pl.sda.jp.miniblog12.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "comment_body", length = 1000)
    private String commentBody;
//    @Column(name = "comment_added") // adnotacja @Column nie jest niebędna - kolumna i tak się stworzy,
//    jeśli chcemy zmienić nazwę kolumny to dodajemy tą adnotację
    private LocalDateTime added;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
