package project.everytime.client.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.everytime.client.user.dto.UserResponse;
import project.everytime.client.user.service.UserQueryService;
import project.everytime.exception.NoSuchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserQueryService userQueryService;

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("form") LoginForm form   ) {
        return "common/login";
    }

    @PostMapping("/login")
    public String loginRequest(
            @Validated @ModelAttribute("form") LoginForm form,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpServletRequest servletRequest, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("field error");
            return "common/login";
        }

        try {
            UserResponse loginUser = userQueryService.login(form.getLoginId(), form.getPassword());
            HttpSession session = servletRequest.getSession();
            session.setAttribute("loginUser", loginUser);
            return "redirect:" + redirectURL;
        } catch (NoSuchException exception) {
            model.addAttribute("loginError", "아이디나 비밀번호를 바르게 입력해주세요.");
            return "common/login";
        }
    }

    @Data
    static class LoginForm {
        @NotBlank
        private String loginId;
        @NotBlank
        private String password;
    }
}
