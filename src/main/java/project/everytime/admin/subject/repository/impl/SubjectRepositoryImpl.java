package project.everytime.admin.subject.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import project.everytime.admin.subject.Subject;
import project.everytime.admin.subject.dto.SortType;
import project.everytime.admin.subject.dto.SubjectResponse;
import project.everytime.admin.subject.dto.SubjectSearchCondition;
import project.everytime.admin.subject.repository.SubjectRepositoryCustom;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static project.everytime.admin.lecture.QLecture.*;
import static project.everytime.admin.major.QMajor.*;
import static project.everytime.admin.subject.QSubject.*;
import static project.everytime.admin.subject.dto.SortType.*;
import static project.everytime.admin.type.QType.*;

public class SubjectRepositoryImpl implements SubjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SubjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<SubjectResponse> findByCondition(Long schoolId, SubjectSearchCondition condition, Pageable pageable) {
        List<Subject> subjects = queryFactory
                .selectFrom(subject)
                .join(subject.lecture, lecture).fetchJoin()
                .join(subject.major, major).fetchJoin()
                .join(subject.type, type).fetchJoin()
                .where(
                        majorEq(condition.getMajorId()),
                        nameLike(condition.getName()),
                        professorLike(condition.getProfessor()),
                        codeLike(condition.getCode()),
                        placeLike(condition.getPlace()),
                        subject.grade.in(condition.getGrades()),
                        subject.type.id.in(condition.getTypes()),
                        subject.credit.in(condition.getCredits())
                )
                .orderBy(
                        sortMethod(condition.getSortType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<SubjectResponse> result = subjects.stream()
                .map(SubjectResponse::new)
                .toList();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }

    private BooleanExpression majorEq(Long majorId) {
        return majorId != null ? subject.major.id.eq(majorId) : null;
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? subject.name.like(name) : null;
    }

    private BooleanExpression professorLike(String professor) {
        return hasText(professor) ? subject.professor.like(professor) : null;
    }

    private BooleanExpression codeLike(String code) {
        return hasText(code) ? subject.code.like(code) : null;
    }

    private BooleanExpression placeLike(String place) {
        return hasText(place) ? subject.place.like(place) : null;
    }

    private OrderSpecifier<?> sortMethod(SortType type) {
        if (type == CODE) {
            return subject.code.asc();
        } else if (type == NAME) {
            return subject.name.asc();
        } else if (type == HIGHRATE) {
            return lecture.rate.desc();
        } else if (type == LOWRATE) {
            return lecture.rate.asc();
        } else if (type == HIGHPOPULAR) {
            return subject.popular.desc();
        } else if (type == LOWPOPULAR) {
            return subject.popular.asc();
        }
        return null;
    }
}
