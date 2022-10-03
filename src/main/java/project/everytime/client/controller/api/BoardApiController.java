package project.everytime.client.controller.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.client.board.Article;
import project.everytime.client.board.Board;
import project.everytime.client.board.BoardType;
import project.everytime.client.board.service.ArticleQueryService;
import project.everytime.client.board.service.BoardQueryService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardQueryService boardQueryService;
    private final ArticleQueryService articleQueryService;

    @PostMapping("/find/community/web")
    public List<MainResponse> test(@RequestBody String test) {
        log.debug("call = /find/community/web");
        List<Board> boards = boardQueryService.findBoards(26L);

        List<MainResponse> result = new ArrayList<>();
        for (Board board : boards) {
            List<Article> articles;
            if (board.getType() == BoardType.LIST) {
                articles = articleQueryService.findRecent4(board.getId());
            } else {
                articles = articleQueryService.findRecent2(board.getId());
            }
            MainResponse boardResponse = new MainResponse(board, articles);
            result.add(boardResponse);
        }

        return result;
    }

    @PostMapping("/find/community/webside")
    public SideResponse side() {
        log.debug("call = /find/community/webside");
        SideResponse sideResponse = new SideResponse();
        List<Article> hotArticles = articleQueryService.hotArticle(26L);
        for (Article article : hotArticles) {
            ArticleResponse articleResponse = new ArticleResponse(article);
            articleResponse.setBoardId(article.getBoard().getId());
            articleResponse.setBoardName(article.getBoard().getName());
            sideResponse.hotArticle.add(articleResponse);
        }
        return sideResponse;
    }

    @Data
    static class MainResponse {
        private Long id;
        private String name;
        private BoardType type;
        private boolean needLogin;
        private List<ArticleResponse> articles = new ArrayList<>();

        public MainResponse(Board board, List<Article> articles) {
            this.id = board.getId();
            this.name = board.getName();
            this.type = board.getType();
            this.needLogin = board.isNeedLogin();
            this.articles = articles.stream()
                    .map(ArticleResponse::new)
                    .toList();
        }
    }

    @Data
    static class SideResponse {
        private List<ArticleResponse> popArticle = new ArrayList<>();
        private List<ArticleResponse> hotArticle = new ArrayList<>();
        //lecture 추가
    }

    @Data
    static class ArticleResponse {
        private Long id;
        private String title;
        private String text;
        private int posvote;
        private int commentCount;
        private String createdDate;

        private Long boardId;
        private String boardName;

        public ArticleResponse(Article article) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.text = article.getText();
            this.posvote = article.getPosvote();
            this.commentCount = article.getComment();
            this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(article.getCreatedDate());
        }
    }
}
