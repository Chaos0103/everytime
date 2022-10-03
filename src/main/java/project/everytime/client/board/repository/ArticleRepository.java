package project.everytime.client.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.client.board.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
}
