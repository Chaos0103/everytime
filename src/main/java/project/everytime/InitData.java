package project.everytime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.repository.SchoolRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final SchoolRepository schoolRepository;

    @PostConstruct
    public void init() {
        School school1 = new School("세종대학교", "본캠", SchoolType.UNIVERSITY, "02-3408-3114", "서울특별시 광진구 능동로 209", City.SEOUL, "http://www.sejong.ac.kr/");
        School school2 = new School("건국대학교", "본캠", SchoolType.UNIVERSITY, "02-450-3114", "서울특별시 광진구 능동로 120", City.SEOUL, "http://www.konkuk.ac.kr/do/Index.do");
        School school3 = new School("한양대학교", "본캠", SchoolType.UNIVERSITY, "02-2220-0114", "서울특별시 성동구 왕십리로 222", City.SEOUL, "https://www.hanyang.ac.kr/");
        school1.addCount();
        schoolRepository.save(school1);
        schoolRepository.save(school2);
        schoolRepository.save(school3);
    }

}
