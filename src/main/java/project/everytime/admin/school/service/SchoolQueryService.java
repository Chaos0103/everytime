package project.everytime.admin.school.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SearchSchool;

import java.util.List;

@Transactional(readOnly = true)
public interface SchoolQueryService {
    //등록된 학교 조회
    List<SchoolResponse> findAllByConditional(SearchSchool search);
    //행정구역, 이름, 학교유형
}
