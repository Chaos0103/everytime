package project.everytime.client.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.FileStore;
import project.everytime.client.UploadFile;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.client.user.service.UserService;
import project.everytime.exception.DuplicateException;
import project.everytime.exception.NoSuchException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileStore fileStore;

    @Override
    public Long addUser(User user) {
        duplicatedLoginId(user.getLoginId());
        duplicatedPhone(user.getPhone());
        duplicatedEmail(user.getEmail());

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    @Override
    public void editEmail(Long userId, String email, String password) {
        User findUser = getUser(userId);
        if (!findUser.getPassword().equals(password)) {
            // TODO: 2022/09/29 예외처리
            throw new IllegalArgumentException();
        }
        findUser.changeEmail(email);
    }

    @Override
    public void editPassword(Long userId, String originPassword, String newPassword) {
        User findUser = getUser(userId);
        //현재 비밀번호 일치 여부
        if (!findUser.getPassword().equals(originPassword)) {
            // TODO: 2022/09/29 예외처리
            throw new IllegalArgumentException();
        }
        findUser.changePassword(newPassword);
    }

    @Override
    public void editNickname(Long userId, String nickname) {
        User findUser = getUser(userId);
        if (findUser.getNicknameModifiedDate().isAfter(LocalDateTime.now().minusDays(30))) {
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
    public void editAdInfoSendAgree(Long userId, Boolean agree) {
        User findUser = getUser(userId);
        findUser.changeAdInfoSendAgree(agree);
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

    private void duplicatedLoginId(String loginId) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        validation(findUser, "이미 사용중인 아이디입니다");
    }

    private void duplicatedPhone(String phone) {
        Optional<User> findUser = userRepository.findByPhone(phone);
        validation(findUser, "이미 등록된 연락처입니다");
    }

    private void duplicatedEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        validation(findUser, "이미 사용중인 이메일입니다");
    }

    private void validation(Optional<User> user, String message) {
        if (user.isPresent()) {
            throw new DuplicateException(message);
        }
    }

    private User getUser(Long userId) {
        User findUser = userRepository.findById(userId).orElse(null);
        if (findUser == null) {
            throw new NoSuchException("등록되지 않은 사용자입니다");
        }
        return findUser;
    }
}
