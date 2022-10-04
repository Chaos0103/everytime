package project.everytime.admin.lecture.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LectureService {

    //강의 등록
    Long addLecture(Long schoolId, String name, String professor);

    //강의 삭제
    void deleteLecture(Long lectureId);
}
