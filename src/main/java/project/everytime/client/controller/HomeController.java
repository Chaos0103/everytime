package project.everytime.client.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.everytime.admin.school.School;
import project.everytime.admin.school.service.SchoolQueryService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.login.Login;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SchoolQueryService schoolQueryService;

    @GetMapping
    public String index(@Login LoginUser loginUser, Model model) {

        if (loginUser == null) {
            log.debug("학교 목록 호출");
            List<School> schoolList = schoolQueryService.findSchoolList("");
            List<SchoolListResponse> schoolListResponses = schoolList.stream()
                    .map(school -> new SchoolListResponse(school.getId(), school.getName().replace("대학교", "대").replace("캠퍼스", "캠"), school.getCount()))
                    .toList();
            model.addAttribute("schoolList", schoolListResponses);
            return "index";
        }

        return "main";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SchoolListResponse {
        private Long id;
        private String name;
        private int count;
    }
}
