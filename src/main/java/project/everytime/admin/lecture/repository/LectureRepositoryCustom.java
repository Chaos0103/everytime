package project.everytime.admin.lecture.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.dto.LectureSearchCondition;

public interface LectureRepositoryCustom {

    Slice<Lecture> findLecturesByCondition(Long schoolId, LectureSearchCondition condition, Pageable pageable);
}
