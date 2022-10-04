package project.everytime.admin.lecture.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.QLecture;
import project.everytime.admin.lecture.dto.LectureSearchCondition;
import project.everytime.admin.lecture.repository.LectureRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static project.everytime.admin.lecture.QLecture.*;

public class LectureRepositoryImpl implements LectureRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LectureRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<Lecture> findLecturesByCondition(Long schoolId, LectureSearchCondition condition, Pageable pageable) {
        List<Lecture> result = queryFactory
                .selectFrom(lecture)
                .where(
                        lecture.school.id.eq(schoolId),
                        nameEq(condition.getName()),
                        professorEq(condition.getProfessor())
                )
                .orderBy(lecture.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? lecture.name.eq(name) : null;
    }

    private BooleanExpression professorEq(String professor) {
        return hasText(professor) ? lecture.professor.eq(professor) : null;
    }
}
