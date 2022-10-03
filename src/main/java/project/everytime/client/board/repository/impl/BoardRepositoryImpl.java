package project.everytime.client.board.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.everytime.client.board.Board;
import project.everytime.client.board.BoardCategory;
import project.everytime.client.board.QBoard;
import project.everytime.client.board.repository.BoardRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.everytime.client.board.QBoard.*;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Board> findBySchoolId(Long schoolId) {
        return queryFactory
                .selectFrom(board)
                .where(
                        board.school.id.eq(schoolId),
                        board.category.eq(BoardCategory.BASIC)
                )
                .fetch();
    }
}
