package project.everytime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static project.everytime.admin.school.City.*;
import static project.everytime.admin.school.SchoolType.*;

@Component
@RequiredArgsConstructor
public class InitData {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        List<School> schoolList = new ArrayList<>();
        schoolList.add(new School("서울대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("연세대학교 신촌캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("연세대학교 미래캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("고려대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("고려대학교 세종캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("서강대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("성균관대학교 인사캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("성균관대학교 자과캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한양대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한양대학교 ERICA캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("중앙대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("중앙대학교 안성캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("경희대학교 국제캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("경희대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한국외국어대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한국외국어대학교 글로벌캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("서울시립대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("건국대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("건국대학교 GLOCAL캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("동국대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("동국대학교 WISE캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("홍익대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("홍익대학교 세종캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("국민대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("숭실대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("세종대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("단국대학교 죽전캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("단국대학교 천안캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("광운대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("명지대학교 인문캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("명지대학교 자연캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("상명대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("상명대학교 천안캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("가톨릭대학교", UNIVERSITY, SEOUL));
        schoolRepository.saveAll(schoolList);

        School school = schoolRepository.findByName("세종대학교").get();
        User user = new User(school, "id", "pw!", "김밍깅", "20010101", "010-1234-5678", "밍술이", "2020", "F", "soju@soju.com", true);
        userRepository.save(user);
    }

}
