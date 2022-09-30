package project.everytime.admin.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.admin.school.School;
import project.everytime.admin.school.service.SchoolQueryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SchoolApiController {

    private final SchoolQueryService schoolQueryService;

    @PostMapping("/find/school/campus/list")
    public List<Campus> test() {
        log.debug("POST: /find/school/campus/list");
        List<School> schoolList = schoolQueryService.findSchoolList("");
        List<Campus> campus = schoolList.stream()
                .map(school -> new Campus(school.getId(), school.getName()))
                .toList();

        return campus;
    }

    @Data
    static class Test {
        List<Campus> campus;

        public Test(List<Campus> campus) {
            this.campus = campus;
        }
    }

    @Data
    @AllArgsConstructor
    static class Campus {
        private Long id;
        private String name;
    }


}
