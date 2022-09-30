package project.everytime.client.user.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.everytime.client.user.User;
import project.everytime.client.user.dto.UserResponse;
import project.everytime.client.user.dto.UserSearchCondition;
import project.everytime.client.user.repository.UserRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static project.everytime.admin.school.QSchool.school;
import static project.everytime.client.user.QUser.*;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<UserResponse> findAllByConditional(UserSearchCondition condition, Pageable pageable) {
        List<User> users = queryFactory
                .selectFrom(user)
                .join(user.school, school).fetchJoin()
                .where(
                        schoolNameEq(condition.getSchoolName()),
                        usernameEq(condition.getUsername())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<UserResponse> content = users.stream()
                .map(UserResponse::new)
                .toList();

        int count = queryFactory
                .selectFrom(user)
                .join(user.school, school).fetchJoin()
                .where(
                        schoolNameEq(condition.getSchoolName()),
                        usernameEq(condition.getUsername())
                )
                .fetch().size();

        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression schoolNameEq(String schoolName) {
        return hasText(schoolName) ? user.school.schoolName.eq(schoolName) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? user.username.eq(username) : null;
    }
}
