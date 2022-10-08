package project.everytime.client.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.UploadFile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attach extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "attach_id", unique = true, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Embedded
    private UploadFile uploadFile;

    public Attach(Article article, UploadFile uploadFile) {
        this.article = article;
        this.uploadFile = uploadFile;
    }
}
