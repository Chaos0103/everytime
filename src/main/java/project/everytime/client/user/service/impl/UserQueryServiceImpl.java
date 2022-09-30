package project.everytime.client.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.everytime.client.user.User;
import project.everytime.client.user.dto.UserSearchCondition;
import project.everytime.client.user.dto.UserResponse;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.client.user.service.UserQueryService;
import project.everytime.exception.NoSuchException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> findAllByConditional(UserSearchCondition condition, Pageable pageable) {
        return userRepository.findAllByConditional(condition, pageable);
    }

    @Override
    public List<UserResponse> findBanUsers() {
        List<User> findUsers = userRepository.findBanUsers();
        return transfer(findUsers);
    }

    @Override
    public List<UserResponse> findByPeriod(Integer month) {
        LocalDateTime period = LocalDateTime.now().minusMonths(month);
        List<User> findUsers = userRepository.findByPeriod(period);
        return transfer(findUsers);
    }

    @Override
    public List<UserResponse> findNonAuth() {
        List<User> findUsers = userRepository.findNonAuth();
        return transfer(findUsers);
    }

    @Override
    public UserResponse login(String loginId, String password) {
        User findUser = userRepository.findLoginByLoginIdAndPassword(loginId, password).orElse(null);
        if (findUser == null) {
            throw new NoSuchException("등록되지 않은 회원입니다");
        }
        return new UserResponse(findUser);
    }

    private List<UserResponse> transfer(List<User> users) {
        return users.stream()
                .map(UserResponse::new)
                .toList();
    }
}
