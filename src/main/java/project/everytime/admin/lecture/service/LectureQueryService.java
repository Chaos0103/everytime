package project.everytime.admin.lecture.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.dto.LectureSearchCondition;

@Transactional(readOnly = true)
public interface LectureQueryService {

    Slice<Lecture> lectureSearch(Long schoolId, LectureSearchCondition condition, Pageable pageable);

    Lecture findLecture(Long lectureId);
}
