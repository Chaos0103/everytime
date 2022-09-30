package project.everytime.client.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.everytime.client.user.User;
import project.everytime.client.user.dto.UserResponse;
import project.everytime.client.user.dto.UserSearchCondition;

import java.util.List;

public interface UserRepositoryCustom {

    Page<UserResponse> findAllByConditional(UserSearchCondition condition, Pageable pageable);
}
