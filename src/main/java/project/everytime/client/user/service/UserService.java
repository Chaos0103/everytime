package project.everytime.client.user.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;

import java.io.IOException;

@Transactional
public interface UserService {

    /**
     * 회원을 등록하는 로직(회원가입)
     * @param user 등록할 회원의 정보
     * @return 등록된 회원의 PK
     * @exception project.everytime.exception.DuplicateException loginId, phone, email 중복시 예외 발생
     */
    Long addUser(User user);

    //이메일 변경
    void editEmail(Long userId, String email, String password);

    //비밀번호 변경
    void editPassword(Long userId, String originPassword, String newPassword);

    //닉네임 변경
    void editNickname(Long userId, String nickname);

    //프로필 이미지 변경
    void editImage(Long userId, MultipartFile file) throws IOException;

    //정보 동의 변경
    void editAdInfoSendAgree(Long userId, Boolean agree);

    //학교 인증 변경
    void editAuthType(Long userId, AuthType type);

    //계정상태 변경
    void editAccountStatus(Long userId, AccountStatus status);

    //회원탈퇴
    Long removeUser(Long userId);
}
