package project.everytime.client.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.client.user.dto.UserResponse;
import project.everytime.client.user.dto.UserSearchCondition;
import project.everytime.client.user.service.UserQueryService;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserQueryService userQueryService;

    @GetMapping
    public Page<UserResponse> users(UserSearchCondition condition, Pageable pageable) {
        return userQueryService.findAllByConditional(condition, pageable);
    }


}
