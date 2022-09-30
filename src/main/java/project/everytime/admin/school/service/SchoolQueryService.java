package project.everytime.admin.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SchoolSearchCondition;

import java.util.List;

@Transactional(readOnly = true)
public interface SchoolQueryService {

    /**
     * 조건에 따라 등록된 학교를 조회하는 로직
     *
     * @param search 학교이름, 학교유형, 행정구역
     * @return 조회된 학교 목록
     */
    Page<SchoolResponse> findAllByConditional(SchoolSearchCondition condition, Pageable pageable);

    List<School> findSchoolList(String name);

    School findSchool(Long schoolId);
}
