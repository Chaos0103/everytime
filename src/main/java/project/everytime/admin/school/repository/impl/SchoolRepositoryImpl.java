package project.everytime.admin.school.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;
import project.everytime.admin.school.City;
import project.everytime.admin.school.QSchool;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.dto.SearchSchool;
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
    public List<School> findAllByConditional(SearchSchool search) {
        return queryFactory
                .selectFrom(school)
                .where(
                        schoolNameEq(search.getSchoolName()),
                        typeEq(search.getType()),
                        cityEq(search.getCity())
                )
                .orderBy(school.lastModifiedDate.desc())
                .fetch();
        // TODO: 2022/09/27 paging 추가
    }

    private BooleanExpression schoolNameEq(String schoolName) {
        return StringUtils.hasText(schoolName) ? school.schoolName.eq(schoolName) : null;
    }

    private BooleanExpression typeEq(SchoolType type) {
        return type != null ? school.type.eq(type) : null;
    }

    private BooleanExpression cityEq(City city) {
        return city != null ? school.city.eq(city) : null;
    }
}
