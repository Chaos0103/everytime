package project.everytime.admin.subject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import project.everytime.admin.subject.dto.SubjectResponse;
import project.everytime.admin.subject.dto.SubjectSearchCondition;
import project.everytime.admin.subject.repository.SubjectRepository;
import project.everytime.admin.subject.service.SubjectQueryService;


@Service
@RequiredArgsConstructor
public class SubjectQueryServiceImpl implements SubjectQueryService {

    private final SubjectRepository subjectRepository;

    @Override
    public Slice<SubjectResponse> findByCondition(Long schoolId, SubjectSearchCondition condition, Pageable pageable) {
        return subjectRepository.findByCondition(schoolId, condition, pageable);
    }
}
