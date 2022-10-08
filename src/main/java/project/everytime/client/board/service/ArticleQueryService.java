package project.everytime.client.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.board.Article;
import project.everytime.client.board.dto.ArticleResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface ArticleQueryService {

    List<Article> findRecent4(Long boardId);

    List<Article> findRecent2(Long boardId);

    //hot article
    List<Article> hotArticle(Long schoolId);

    Page<ArticleResponse> findMyArticle(Long userId, Pageable pageable);
}
