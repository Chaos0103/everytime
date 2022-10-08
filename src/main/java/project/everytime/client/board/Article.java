package project.everytime.client.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.UploadFile;
import project.everytime.client.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "article_id", unique = true, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String title;
    @Lob
    @Column(nullable = false)
    private String text;
    private int posvote;
    private int comment;
    private int scrapCount;
    private boolean anonymous;
    private boolean question;
    @Column(insertable = false)
    private LocalDateTime hotArticleCreatedTime;

    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Attach> attaches = new ArrayList<>();

    public Article(User user, Board board, String title, String text, int posvote, int comment, int scrapCount, boolean anonymous, boolean question) {
        this.user = user;
        this.board = board;
        this.title = title;
        this.text = text;
        this.posvote = posvote;
        this.comment = comment;
        this.scrapCount = scrapCount;
        this.anonymous = anonymous;
        this.question = question;
    }

    //==생성 메서드==//
    public static Article createArticle(User user, Board board, String title, String text, boolean anonymous, boolean question, List<UploadFile> uploadFiles) {
        Article article = new Article(user, board, title, text, 0, 0, 0, anonymous, question);

        for (UploadFile uploadFile : uploadFiles) {
            article.addArticleImage(new Attach(article, uploadFile));
        }
        return article;
    }

    //==연관관계 편의 메서드==//
    public void addArticleImage(Attach image) {
        attaches.add(image);
    }

    //==비즈니스 로직==//
    public void editArticle(String title, String text, boolean anonymous, boolean question, List<UploadFile> uploadFiles) {
        this.title = title;
        this.text = text;
        this.anonymous = anonymous;
        this.question = question;
        this.attaches.clear();
        for (UploadFile uploadFile : uploadFiles) {
            this.attaches.add(new Attach(this, uploadFile));
        }
    }

    public void addPosvote() {
        this.posvote++;
        if (this.posvote == 10) {
            this.hotArticleCreatedTime = LocalDateTime.now();
        }
    }

    public void addComment() {
        this.comment++;
    }

    public void addScrapCount() {
        this.scrapCount++;
    }
}
