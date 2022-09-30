package project.everytime.admin.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SchoolSearchCondition;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.admin.school.service.SchoolQueryService;
import project.everytime.exception.NoSuchException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolQueryServiceImpl implements SchoolQueryService {

    private final SchoolRepository schoolRepository;

    @Override
    public Page<SchoolResponse> findAllByConditional(SchoolSearchCondition condition, Pageable pageable) {
        return schoolRepository.findAllByConditional(condition, pageable);
    }

    @Override
    public List<School> findSchoolList(String name) {
        return schoolRepository.findSchoolList(name);
    }

    @Override
    public School findSchool(Long schoolId) {
        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new NoSuchException("등록되지 않은 학교입니다");
        }
        return findSchool;
    }
}
