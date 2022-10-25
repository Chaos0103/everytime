package project.everytime.client.book.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;
import project.everytime.client.book.Item;
import project.everytime.client.book.QItemImage;
import project.everytime.client.book.repository.ItemRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static project.everytime.client.book.QBook.*;
import static project.everytime.client.book.QItem.*;
import static project.everytime.client.book.QItemImage.*;

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
                .limit(40)
                .offset(start)
                .fetch();
    }

    @Override
    public Optional<Item> findItem(Long itemId) {
        Item result = queryFactory
                .select(item).distinct()
                .from(item)
                .join(item.book, book).fetchJoin()
                .leftJoin(item.images, itemImage).fetchJoin()
                .where(item.id.eq(itemId))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    private BooleanExpression isMine(Integer mine, Long userId) {
        return mine == 1 ? item.user.id.eq(userId) : null;
    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? item.book.title.contains(title) : null;
    }
}
