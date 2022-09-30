package project.everytime.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.everytime.client.user.service.UserQueryService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserQueryService userQueryService;

    @GetMapping("/login")
    public String loginPage() {
        return "common/login";
    }
}
