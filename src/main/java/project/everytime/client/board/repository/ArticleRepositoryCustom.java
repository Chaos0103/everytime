package project.everytime.client.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.everytime.client.board.Article;
import project.everytime.client.board.dto.ArticleResponse;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findTop4(Long boardId);

    List<Article> findTop2(Long boardId);

    List<Article> findHotArticle(Long schoolId);

    Page<ArticleResponse> findMyArticle(Long userId, Pageable pageable);
}
