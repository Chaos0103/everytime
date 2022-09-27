package project.everytime.admin.school.repository;

import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SearchSchool;

import java.util.List;

public interface SchoolRepositoryCustom {

    /**
     * 전체, 학교이름, 학교유형, 행정구역 별 조회
     * @param search 조회 조건
     * @return 학교 목록
     */
    List<School> findAllByConditional(SearchSchool search);
}
