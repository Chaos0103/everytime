package project.everytime.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.everytime.admin.school.School;
import project.everytime.admin.school.service.SchoolQueryService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class JoinController {

    private final SchoolQueryService schoolQueryService;

    @GetMapping
    public String selectSchool(Model model) {
        List<School> schoolList = schoolQueryService.findSchoolList(null);
        model.addAttribute("list", schoolList);
        return "common/join/register";
    }

    @PostMapping("/agreement")
    public String test() {
        return "common/join2";
    }
}
