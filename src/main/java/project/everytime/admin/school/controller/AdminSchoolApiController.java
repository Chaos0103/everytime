package project.everytime.admin.school.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.dto.SchoolEdit;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SchoolSearchCondition;
import project.everytime.admin.school.service.SchoolQueryService;
import project.everytime.admin.school.service.SchoolService;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/schools")
public class AdminSchoolApiController {

    private final SchoolService schoolService;
    private final SchoolQueryService schoolQueryService;

    @GetMapping
    public Page<SchoolResponse> schools(@RequestBody SchoolSearchCondition condition, Pageable pageable) {
        log.debug("search schools: {}, {}", condition, pageable);
        return schoolQueryService.findAllByConditional(condition, pageable);
    }

    @PostMapping
    public void addSchool(@RequestBody SchoolAddRequest request) {
        School school = new School(request.getName(), request.getType(), request.getCity());
        Long schoolId = schoolService.addSchool(school);
    }

    // TODO: 2022/09/29 PatchMapping 확인하기
    @PatchMapping("/{schoolId}")
    public void editSchool(@RequestBody SchoolEditRequest request, @PathVariable Long schoolId) {
        SchoolEdit schoolEdit = new SchoolEdit(request.getName(), request.getType(), request.getTel(), request.getAddress(), request.getCity(), request.getUrl());
        schoolService.editSchool(schoolId, schoolEdit);
    }

    @DeleteMapping("/{schoolId}")
    public void removeSchool(@PathVariable Long schoolId) {
        Long id = schoolService.removeSchool(schoolId);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SchoolAddRequest {
        private String name;
        private SchoolType type;
        private String tel;
        private String address;
        private City city;
        private String url;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SchoolEditRequest {
        private String name;
        private SchoolType type;
        private String tel;
        private String address;
        private City city;
        private String url;
    }

}
