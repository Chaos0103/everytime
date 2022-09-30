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
        schoolList.add(new School("서강대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("한양대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("건국대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("국민대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("숭실대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("세종대학교", UNIVERSITY, SEOUL));
        schoolRepository.saveAll(schoolList);

        School school = schoolRepository.findByName("세종대학교").get();
        User user = new User(school, "id", "pw!", "김밍깅", "20010101", "010-1234-5678", "밍술이", "2020", "F", "soju@soju.com", true);
        userRepository.save(user);
    }

}
