package project.everytime.admin.school.repository;

import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SearchSchool;

import java.util.List;

public interface SchoolRepositoryCustom {

    List<School> findAllByConditional(SearchSchool search);
}
