package project.everytime.client.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.client.board.dto.ArticleResponse;
import project.everytime.client.board.service.ArticleQueryService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.login.Login;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleQueryService articleQueryService;

    // TODO: 2022/10/08 페이징 추가
    @PostMapping("/find/board/article/list")
    public Page<ArticleResponse> test(
            @RequestParam("id") String id,
            @RequestParam("limit_num") Integer size,
            @RequestParam("start_num") Integer startNum,
            @RequestParam(value = "moiminfo", required = false) Boolean moiminfo,
            @Login LoginUser loginUser) {
        log.debug("id: {}, size: {}, startNum: {}, moiminfo: {}", id, size, startNum, moiminfo);
        PageRequest pageRequest = PageRequest.of(startNum / size, size);
        return articleQueryService.findMyArticle(loginUser.getId(), pageRequest);
    }
}
