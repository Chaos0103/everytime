package project.everytime.client.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.everytime.client.user.service.UserQueryService;
import project.everytime.exception.NoSuchException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/forgot")
public class ForgotController {

    private final UserQueryService userQueryService;

    @GetMapping
    public String forgotPage(@ModelAttribute("form") ForgotIdForm form) {
        return "forgot/forgot";
    }

    @PostMapping
    public String findLoginId(@ModelAttribute("form") ForgotIdForm form, Model model) {

        try {
            String email = userQueryService.forgotLoginId(form.getEmail());
            log.debug("find email={}", email);
            return "redirect:/";
        } catch (NoSuchException e) {
            log.warn(e.getMessage());
            model.addAttribute("exception", e.getMessage());
            return "forgot/forgot";
        }
    }

    @Data
    static class ForgotIdForm {
        private String email;
    }
}
