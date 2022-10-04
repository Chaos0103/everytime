package project.everytime.admin.major.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.major.dto.MajorResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface MajorQueryService {

    //전공 전체 조회
    List<MajorResponse> findMajors(Long schoolId);
}
