package project.everytime.admin.subject.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.subject.SubjectAddDto;
import project.everytime.admin.subject.dto.TimeplaceAddDto;

import java.util.List;

@Transactional
public interface SubjectService {

    Long addSubject(Long majorId, Long typeId, Long lectureId, SubjectAddDto subjectAddDto, List<TimeplaceAddDto> timeplaceAddDtos);
}
