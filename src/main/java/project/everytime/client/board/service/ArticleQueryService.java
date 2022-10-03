package project.everytime.client.board.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.board.Article;

import java.util.List;

@Transactional(readOnly = true)
public interface ArticleQueryService {

    List<Article> findRecent4(Long boardId);

    List<Article> findRecent2(Long boardId);

    //hot article
    List<Article> hotArticle(Long schoolId);

    //게시물 단건조회
}
