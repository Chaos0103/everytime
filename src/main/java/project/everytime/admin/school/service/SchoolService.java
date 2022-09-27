package project.everytime.admin.school.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.UpdateSchool;

@Transactional
public interface SchoolService {

    /**
     * 학교를 등록하는 로직
     * @param school 등록할 학교 정보
     * @return 등록된 학교의 id
     * @exception project.everytime.exception.DuplicateException 학교, 전화번호, 주소, url 중복시 예외 발생
     */
    Long addSchool(School school);

    //학교정보수정
    void editSchool(UpdateSchool updateSchool);

    //학교삭제
    Long removeSchool(Long schoolId);
}
