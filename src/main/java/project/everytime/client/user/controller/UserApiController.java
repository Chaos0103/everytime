package project.everytime.client.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.everytime.admin.school.School;
import project.everytime.admin.school.service.SchoolQueryService;
import project.everytime.client.user.User;
import project.everytime.client.user.service.UserService;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;
    private final SchoolQueryService schoolQueryService;

    @PostMapping
    public void joinUser(@RequestBody JoinRequest request) {
        School school = schoolQueryService.findSchool(request.schoolId);
        User user = new User(school, request.getLoginId(), request.getPassword(), request.getUsername(), request.getBirth(),
                request.getPhone(), request.getNickname(), request.getYearOfAdmission(), request.getSex(), request.getEmail(), request.getAdInfoSendAgree());

        Long userId = userService.addUser(user);

        log.info("joinUser={}", userId);
    }

    @PutMapping("/{userId}/email")
    public void editEmail(@RequestBody EditEmailRequest request, @PathVariable Long userId) {
        userService.editEmail(userId, request.getEmail(), request.getPassword());
    }


    @PutMapping("/{userId}/password")
    public void editPassword(@RequestBody EditPasswordRequest request, @PathVariable Long userId) {
        //새 비밀번호, 새 비밀번호 확인 일치 여부
        if (!request.getNewPassword().equals(request.getCheckNewPassword())) {
            // TODO: 2022/09/29 예외처리
            throw new IllegalArgumentException();
        }
        userService.editPassword(userId, request.getOriginPassword(), request.getNewPassword());
    }

    @PutMapping("/{userId}/nickname")
    public void editNickname(@RequestBody EditNicknameRequest request ,@PathVariable Long userId) {
        userService.editNickname(userId, request.getNickname());
    }

    @PutMapping("/{userId}/adInfoSendAgree")
    public void editAdInfoSendAgree(@RequestBody EditAdInfoSendAgreeRequest request, @PathVariable Long userId) {
        userService.editAdInfoSendAgree(userId, request.getAdInfoSendAgree());
    }




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class JoinRequest {
        private Long schoolId;
        private String loginId;
        private String password;
        private String username;
        private String birth;
        private String phone;
        private String nickname;
        private String yearOfAdmission;
        private String sex;
        private String email;
        private Boolean adInfoSendAgree;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EditEmailRequest {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EditPasswordRequest {
        private String newPassword;
        private String checkNewPassword;
        private String originPassword;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EditNicknameRequest {
        private String nickname;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EditAdInfoSendAgreeRequest {
        private Boolean adInfoSendAgree;
    }
}
