package project.everytime.admin.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SchoolResponse;
import project.everytime.admin.school.dto.SearchSchool;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.admin.school.service.SchoolQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolQueryServiceImpl implements SchoolQueryService {

    private final SchoolRepository schoolRepository;

    @Override
    public List<SchoolResponse> findAllByConditional(SearchSchool search) {
        List<School> findSchools = schoolRepository.findAllByConditional(search);
        return findSchools.stream()
                .map(SchoolResponse::new)
                .toList();
    }
}
