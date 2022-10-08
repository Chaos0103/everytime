package project.everytime.client.board.dto;

import lombok.Data;
import project.everytime.client.board.Article;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ArticleResponse {
    private Long id;
    private int isMine;
    private String title;
    private String text;
    private String createdDate;
    private int posvote;
    private int comment;
    private int commentAnonym;
    private int scrapCount;
    private Long boardId;
    private String boardName;
    private String userType;
    private Long userId;
    private String userNickname;
    private String userPicture;
    private List<AttachResponse> attaches;

    public ArticleResponse(Long userId, Article article) {
        this.id = article.getId();
        this.isMine = userId.equals(article.getUser().getId()) ? 1 : 0;
        this.title = article.getTitle();
        this.text = article.getText();
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(article.getCreatedDate());
        this.posvote = article.getPosvote();
        this.comment = article.getComment();
        this.commentAnonym = 0;
        this.scrapCount = article.getScrapCount();
        this.boardId = article.getBoard().getId();
        this.boardName = article.getBoard().getName();
        this.userType = "";
        this.userId = article.isAnonymous() ? 0L : article.getUser().getId();
        this.userNickname = article.isAnonymous() ? "익명" : article.getUser().getNickname();
        this.userPicture = article.getUser().getUploadFile().getStoreFileName();
        this.attaches = article.getAttaches().stream()
                .map(AttachResponse::new)
                .toList();
    }
}
