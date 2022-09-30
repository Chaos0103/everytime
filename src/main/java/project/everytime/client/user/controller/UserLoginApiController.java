package project.everytime.client.user.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.everytime.client.user.dto.UserResponse;
import project.everytime.client.user.service.UserQueryService;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class UserLoginApiController {

    private final UserQueryService userQueryService;

    @PostMapping
    public Error login(@RequestBody LoginRequest request) {
        log.info(request.toString());
        UserResponse loginUser = userQueryService.login(request.getLoginId(), request.getPassword());
        return null;
    }

    @Data
    static class LoginRequest {
        private String loginId;
        private String password;
    }
}
