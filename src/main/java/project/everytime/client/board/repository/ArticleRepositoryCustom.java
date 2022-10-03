package project.everytime.client.board.repository;

import project.everytime.client.board.Article;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findTop4(Long boardId);

    List<Article> findTop2(Long boardId);

    List<Article> findHotArticle(Long schoolId);
}
