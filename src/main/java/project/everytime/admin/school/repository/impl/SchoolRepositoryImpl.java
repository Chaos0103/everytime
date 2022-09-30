package project.everytime.admin.school.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SchoolSearchCondition;
import project.everytime.admin.school.repository.SchoolRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.everytime.admin.school.QSchool.*;

public class SchoolRepositoryImpl implements SchoolRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SchoolRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<SchoolResponse> findAllByConditional(SchoolSearchCondition condition, Pageable pageable) {
        List<School> schools = queryFactory
                .selectFrom(school)
                .where(
                        nameEq(condition.getName()),
                        typeEq(condition.getType()),
                        cityEq(condition.getCity())
                )
                .orderBy(school.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<SchoolResponse> content = schools.stream()
                .map(SchoolResponse::new)
                .toList();

        int count = queryFactory
                .selectFrom(school)
                .where(
                        nameEq(condition.getName()),
                        typeEq(condition.getType()),
                        cityEq(condition.getCity())
                )
                .fetch().size();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public List<School> findSchoolList(String name) {
        return queryFactory
                .selectFrom(school)
                .where(
                        nameEq(name)
                )
                .orderBy(school.count.desc())
                .fetch();
        // TODO: 2022/09/29 like로 변경
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? school.name.contains(name) : null;
    }

    private BooleanExpression typeEq(SchoolType type) {
        return type != null ? school.type.eq(type) : null;
    }

    private BooleanExpression cityEq(City city) {
        return city != null ? school.city.eq(city) : null;
    }
}
