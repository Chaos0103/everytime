package project.everytime.client.board.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.everytime.client.board.Article;
import project.everytime.client.board.QArticle;
import project.everytime.client.board.QBoard;
import project.everytime.client.board.repository.ArticleRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.everytime.client.board.QArticle.*;
import static project.everytime.client.board.QBoard.*;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ArticleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Article> findTop4(Long boardId) {
        return queryFactory
                .selectFrom(article)
                .where(article.board.id.eq(boardId))
                .orderBy(article.createdDate.desc())
                .limit(4)
                .fetch();
    }

    @Override
    public List<Article> findTop2(Long boardId) {
        return queryFactory
                .selectFrom(article)
                .where(article.board.id.eq(boardId))
                .orderBy(article.createdDate.desc())
                .limit(2)
                .fetch();
    }

    @Override
    public List<Article> findHotArticle(Long schoolId) {
        return queryFactory
                .selectFrom(article)
                .join(article.board, board).fetchJoin()
                .where(
                        board.school.id.eq(schoolId),
                        article.hotArticleCreatedTime.isNotNull()
                )
                .orderBy(article.hotArticleCreatedTime.desc())
                .limit(4)
                .fetch();
    }
}
