package project.everytime.admin.major.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.major.Major;
import project.everytime.admin.major.dto.MajorResponse;
import project.everytime.admin.major.repository.MajorRepository;
import project.everytime.admin.major.service.MajorQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorQueryServiceImpl implements MajorQueryService {

    private final MajorRepository majorRepository;

    @Override
    public List<MajorResponse> findMajors(Long schoolId) {
        List<Major> majors = majorRepository.findBySchoolId(schoolId);
        return majors.stream()
                .map(MajorResponse::new)
                .toList();
    }
}
