package project.everytime.admin.major.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MajorService {

    //등록
    Long addMajor(Long schoolId, Long parentId, String name, int order);

    //수정
    void editMajor(Long majorId, Long parentId, String name, int order);

    //삭제
    void deleteMajor(Long majorId);
}
