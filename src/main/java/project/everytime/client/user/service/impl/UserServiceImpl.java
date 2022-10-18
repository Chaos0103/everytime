package project.everytime.client.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.FileStore;
import project.everytime.admin.school.School;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.client.UploadFile;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;
import project.everytime.client.user.dto.UserJoinDto;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.client.user.service.UserService;
import project.everytime.exception.DuplicateException;
import project.everytime.exception.NoSuchException;
import project.everytime.exception.NotEqualsException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static project.everytime.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final FileStore fileStore;

    @Override
    public Long addUser(Long schoolId, UserJoinDto data) {
        School findSchool = getSchool(schoolId);

        duplicatedLoginId(data.getLoginId());
        duplicatedPhone(data.getPhone());
        duplicatedEmail(data.getEmail());
        duplicatedNickname(data.getNickname());

        User user = User.createUser(findSchool, data.getLoginId(), data.getPassword(), data.getUsername(), data.getBirth(), data.getPhone(), data.getNickname(), data.getEnterYear(), data.getSex(), data.getEmail(), data.getAgreementAd());
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    @Override
    public void editEmail(Long userId, String email, String password) {
        User findUser = getUser(userId);
        if (!isEqualsPassword(findUser.getPassword(), password)) {
            throw new NotEqualsException(NOT_EQUALS_EXCEPTION_PASSWORD);
        }
        findUser.changeEmail(email);
    }

    @Override
    public void editPassword(Long userId, String originPassword, String newPassword) {
        User findUser = getUser(userId);
        if (!isEqualsPassword(findUser.getPassword(), originPassword)) {
            throw new NotEqualsException(NOT_EQUALS_EXCEPTION_PASSWORD);
        }
        findUser.changePassword(newPassword);
    }

    @Override
    public void editNickname(Long userId, String nickname) {
        User findUser = getUser(userId);
        if (findUser.getNickname().getNicknameModifiedDate().isAfter(LocalDateTime.now().minusDays(30))) {
            // TODO: 2022/09/29 예외처리
            throw new IllegalArgumentException();
        }
        findUser.changeNickname(nickname);
    }

    @Override
    public void editImage(Long userId, MultipartFile file) throws IOException {
        User findUser = getUser(userId);
        UploadFile uploadFile = fileStore.storeFile(file);
        findUser.changeUploadFile(uploadFile);
    }

    @Override
    public void editAccountStatus(Long userId, AccountStatus status) {
        User findUser = getUser(userId);
        findUser.changeStatus(status);
    }

    @Override
    public void editAdInfoSendAgree(Long userId, int agreementAd) {
        User findUser = getUser(userId);
        findUser.changeAdInfoSendAgree(agreementAd);
    }

    @Override
    public void editAuthType(Long userId, AuthType type) {
        User findUser = getUser(userId);
        findUser.changeAuthType(type);
    }

    @Override
    public Long removeUser(Long userId) {
        User findUser = getUser(userId);
        userRepository.delete(findUser);
        return userId;
    }

    //== 검증 로직 ==//
    private void duplicatedLoginId(String loginId) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        validation(findUser, DUPLICATE_EXCEPTION_LOGIN_ID);
    }

    private void duplicatedPhone(String phone) {
        Optional<User> findUser = userRepository.findByPhone(phone);
        validation(findUser, DUPLICATE_EXCEPTION_PHONE);
    }

    private void duplicatedEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        validation(findUser, DUPLICATE_EXCEPTION_EMAIL);
    }

    private void duplicatedNickname(String nickname) {
        Optional<User> findUser = userRepository.findByNickname(nickname);
        validation(findUser, DUPLICATE_EXCEPTION_NICKNAME);
    }

    private void validation(Optional<User> user, String message) {
        if (user.isPresent()) {
            throw new DuplicateException(message);
        }
    }

    //== 조회 메서드 ==//
    private User getUser(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_USER);
        }
        return findUser.get();
    }

    private School getSchool(Long schoolId) {
        Optional<School> findSchool = schoolRepository.findById(schoolId);
        if (findSchool.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_SCHOOL);
        }
        return findSchool.get();
    }

    //== 편의 메서드 ==//
    private boolean isEqualsPassword(String password, String target) {
        return password.equals(target);
    }
}
