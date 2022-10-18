package project.everytime.client.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.client.user.dto.UserSearchCondition;
import project.everytime.client.user.dto.UserResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface UserQueryService {

    //회원 전체 조회 - 대학별, 이름,
    Page<UserResponse> findAllByConditional(UserSearchCondition condition, Pageable pageable);

    //동의한사람
    //이용 정지 회원 조회
    List<UserResponse> findBanUsers();

    //기간 별 가입한사람 조회
    List<UserResponse> findByPeriod(Integer month);

    //인증 대기 회원 조회
    List<UserResponse> findNonAuth();

    //로그인을 위한 조회
    LoginUser login(String loginId, String password);

    String forgotLoginId(String email);

    Long forgotPassword(String loginId);
}
