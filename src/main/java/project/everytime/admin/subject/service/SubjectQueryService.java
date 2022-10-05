package project.everytime.admin.subject.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.subject.dto.SubjectResponse;
import project.everytime.admin.subject.dto.SubjectSearchCondition;

@Transactional(readOnly = true)
public interface SubjectQueryService {

    Slice<SubjectResponse> findByCondition(Long schoolId, SubjectSearchCondition condition, Pageable pageable);


}
