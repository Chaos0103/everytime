package project.everytime.client.board.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.everytime.client.board.Article;
import project.everytime.client.board.QArticle;
import project.everytime.client.board.QBoard;
import project.everytime.client.board.dto.ArticleResponse;
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

    @Override
    public Page<ArticleResponse> findMyArticle(Long userId, Pageable pageable) {
        List<Article> result = queryFactory
                .selectFrom(article)
                .join(article.board, board).fetchJoin()
                .where(article.user.id.eq(userId))
                .orderBy(article.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<ArticleResponse> content = result.stream()
                .map(article -> new ArticleResponse(userId, article))
                .toList();

        int total = queryFactory
                .selectFrom(article)
                .where(article.user.id.eq(userId))
                .fetch().size();
        return new PageImpl<>(content, pageable, total);
    }

}
