package project.everytime.admin.subject.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import project.everytime.admin.subject.dto.SubjectResponse;
import project.everytime.admin.subject.dto.SubjectSearchCondition;

public interface SubjectRepositoryCustom {

    Slice<SubjectResponse> findByCondition(Long schoolId, SubjectSearchCondition condition, Pageable pageable);
}
