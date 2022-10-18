package project.everytime.client.controller.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.client.user.dto.UserJoinDto;
import project.everytime.client.user.service.UserService;
import project.everytime.exception.NoSuchException;

import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.*;
import static project.everytime.exception.ExceptionMessage.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinApiController {

    private final UserService userService;

    @PostMapping("/save/user")
    public String join(@RequestBody UserJoinRequest request) {

        if (!hasText(request.getLoginId())) {
            return "-1";
        }
        if (!(4 <= request.getLoginId().length() && request.getLoginId().length() <= 20)) {
            return "-2";
        }
        boolean loginIdPattern = Pattern.matches("^[a-z0-9]+$", request.getLoginId());
        if (!loginIdPattern) {
            return "-3";
        }
        if (!hasText(request.getPassword())) {
            return "-5";
        }
        if (!(8 <= request.getPassword().length() && request.getPassword().length() <= 20)) {
            return "-6";
        }
        if (request.getLoginId().equals(request.getPassword())) {
            return "-17";
        }
//        boolean passwordPatternAlpha = Pattern.matches("[a-z]", request.getPassword());
//        boolean passwordPatternNumber = Pattern.matches("[0-9]", request.getPassword());
//        boolean passwordPatternSpecial = Pattern.matches("[^a-z0-9]", request.getPassword());
//        if (!(passwordPatternAlpha && passwordPatternNumber && passwordPatternSpecial)) {
//            return "-21";
//        }
        if (!hasText(request.getEmail())) {
            return "-12";
        }
        boolean emailPattern = Pattern.matches("^((\\w|[`~!$#%^&*\\-={}|'./?])+)@((\\w|[\\-\\.])+)\\.([A-Za-z]+)$", request.getEmail());
        if (!emailPattern) {
            return "-18";
        }
        if (!hasText(request.getNickname())) {
            return "-9";
        }
        if (request.getNickname().length() > 10) {
            return "-10";
        }
//        boolean nicknamePattern = Pattern.matches("\\s", request.getNickname());
//        if (!nicknamePattern) {
//            return "-16";
//        }

        //alert('일부 정보가 존재하지 않습니다. 정상적인 회원가입 절차에 따라 다시 진행해주세요.'); -7, -14, -15

        log.debug(String.valueOf(request));
        try {
            UserJoinDto userJoinDto = new UserJoinDto(request.getLoginId(), request.getPassword(), request.getEmail(), request.getNickname(), request.getEnterYear(), request.getAgreementAd(), request.getUsername(), request.getBirth(), request.getSex(), request.getPhone());
            Long userId = userService.addUser(request.getSchoolId(), userJoinDto);
            log.debug("userId={}", userId);
            return String.valueOf(userId);
        } catch (NoSuchException e) {
            log.debug(e.getMessage());
            if (e.getMessage().equals(DUPLICATE_EXCEPTION_LOGIN_ID)) {
                return "-4";
            } else if (e.getMessage().equals(DUPLICATE_EXCEPTION_EMAIL)) {
                return "-13";
            } else if (e.getMessage().equals(DUPLICATE_EXCEPTION_PHONE)) {
                return "-19";
            } else {
                return "-11";
            }
        }
        //alert('반복적인 탈퇴 및 재가입은 일정 기간 제한됩니다.'); -20
        //alert('더 이상 회원가입을 진행할 수 없습니다. [문의하기]를 통해 문의해주시기 바랍니다.'); 0
    }

    @Data
    static class UserJoinRequest {
        private String loginId;
        private String password;
        private String email;
        private String nickname;
        private String enterYear;
        private Long schoolId;
        private Integer agreementAd;

        private String username;
        private String birth;
        private String sex;
        private String phone;
    }
}
