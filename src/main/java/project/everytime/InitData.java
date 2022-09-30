package project.everytime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.repository.SchoolRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final SchoolRepository schoolRepository;

    @PostConstruct
    public void init() {
        List<School> schoolList = new ArrayList<>();
        schoolList.add(new School("서울대", "본캠", SchoolType.UNIVERSITY, "02-880-5114", "서울특별시 관악구 관악로 1", City.SEOUL, "https://www.snu.ac.kr/"));
        schoolList.add(new School("세종대", "본캠", SchoolType.UNIVERSITY, "02-3408-3114", "서울특별시 광진구 능동로 209", City.SEOUL, "http://www.sejong.ac.kr/"));
        schoolList.add(new School("건국대", "본캠", SchoolType.UNIVERSITY, "02-450-3114", "서울특별시 광진구 능동로 120", City.SEOUL, "http://www.konkuk.ac.kr/do/Index.do"));
        schoolList.add(new School("한양대", "본캠", SchoolType.UNIVERSITY, "02-2220-0114", "서울특별시 성동구 왕십리로 222", City.SEOUL, "https://www.hanyang.ac.kr/"));
        schoolRepository.saveAll(schoolList);
    }

}
