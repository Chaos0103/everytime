package project.everytime.client.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.client.user.service.UserService;
import project.everytime.login.Login;

import javax.validation.constraints.NotBlank;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String myPage(@Login LoginUser loginUser, Model model) {
        log.debug("loginUser = {}", loginUser);
        model.addAttribute("loginUser", loginUser);
        return "my/my";
    }

    @GetMapping("/password")
    public String passwordPage(@ModelAttribute("form") PasswordEditForm form) {
        return "my/password";
    }

    @PostMapping("/password")
    public String changePassword(
            @Login LoginUser loginUser,
            @Validated @ModelAttribute("form") PasswordEditForm form,
            BindingResult bindingResult) {
        log.debug("call = /my/password");

        if (bindingResult.hasErrors()) {
            return "my/password";
        }

        if (!form.getNewPassword().equals(form.getCheckNewPassword())) {
            return "my/password";
        }

        userService.editPassword(loginUser.getId(), form.getOriginPassword(), form.getNewPassword());

        return "redirect:/logout";
    }

    @GetMapping("/email")
    public String emailPage(@ModelAttribute("form") EmailEditForm form) {
        return "my/email";
    }

    @PostMapping("/email")
    public String changeEmail(
            @Login LoginUser loginUser,
            @ModelAttribute("form") EmailEditForm form) {
        return "redirect:/my";
    }

    @GetMapping("/nickname")
    private String nicknamePage(@Login LoginUser loginUser, @ModelAttribute("form") NicknameEditForm form) {
        form.setNickname(loginUser.getNickname());
        return "my/nickname";
    }

    @Data
    static class PasswordEditForm {
        private String newPassword;
        private String checkNewPassword;
        private String originPassword;
    }

    @Data
    static class EmailEditForm {
        private String email;
        private String password;
    }

    @Data
    static class NicknameEditForm {
        private String nickname;
    }
}
