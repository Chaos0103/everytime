package project.everytime.client.book.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;
import project.everytime.client.book.Item;
import project.everytime.client.book.repository.ItemRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static project.everytime.client.book.QBook.*;
import static project.everytime.client.book.QItem.*;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> findItemList(Long userId, Integer mine, String title, Integer start) {
        return queryFactory
                .selectFrom(item)
                .join(item.book, book).fetchJoin()
                .where(
                        isMine(mine, userId),
                        titleContains(title)
                )
                .limit(start + 40)
                .offset(start)
                .fetch();
    }

    private BooleanExpression isMine(Integer mine, Long userId) {
        return mine == 1 ? item.user.id.eq(userId) : null;
    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? item.book.title.contains(title) : null;
    }
}
