package project.everytime.admin.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SchoolSearchCondition;

import java.util.List;

public interface SchoolRepositoryCustom {

    /**
     * 전체, 학교이름, 학교유형, 행정구역 별 조회
     *
     * @param search 조회 조건
     * @return 학교 목록
     */
    Page<SchoolResponse> findAllByConditional(SchoolSearchCondition search, Pageable pageable);

    List<School> findSchoolList(String name);
}
