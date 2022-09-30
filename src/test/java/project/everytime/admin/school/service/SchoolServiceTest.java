package project.everytime.admin.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.exception.DuplicateException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SchoolServiceTest {

    @Autowired SchoolService schoolService;
    @Autowired SchoolRepository schoolRepository;

    @Test
    @DisplayName("학교등록")
    void addSchool() {
        //given
        School school = new School("세종대학교", "본캠", SchoolType.UNIVERSITY, "02-3408-3114", "서울특별시 광진구 능동로 209", City.SEOUL, "http://www.sejong.ac.kr/");
        //when
        Long schoolId = schoolService.addSchool(school);
        //then
        assertThat(school.getId()).isEqualTo(schoolId);
    }

    @Test
    @DisplayName("학교 중복 예외")
    void duplicatedSchool() {
        //given
        createSchool();
        School school = new School("세종대학교", "본캠", SchoolType.UNIVERSITY, "02-1234-5678", "서울특별시 광진구 능동로 111", City.SEOUL, "url");

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> schoolService.addSchool(school));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 학교입니다");
    }

    @Test
    @DisplayName("전화번호 중복 예외")
    void duplicatedTel() {
        //given
        createSchool();
        School school = new School("건국대학교", "본캠", SchoolType.UNIVERSITY, "02-3408-3114", "서울특별시 광진구 능동로 111", City.SEOUL, "url");

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> schoolService.addSchool(school));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 연락처입니다");
    }

    @Test
    @DisplayName("주소 중복 예외")
    void duplicatedAddress() {
        //given
        createSchool();
        School school = new School("건국대학교", "본캠", SchoolType.UNIVERSITY, "02-1234-5678", "서울특별시 광진구 능동로 209", City.SEOUL, "url");

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> schoolService.addSchool(school));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 주소입니다");
    }

    @Test
    @DisplayName("url 중복 예외")
    void duplicatedUrl() {
        //given
        createSchool();
        School school = new School("건국대학교", "본캠", SchoolType.UNIVERSITY, "02-1234-5678", "서울특별시 광진구 능동로 100", City.SEOUL, "http://www.sejong.ac.kr/");

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> schoolService.addSchool(school));

        //then
        assertThat(exception.getMessage()).isEqualTo("이미 등록된 홈페이지 주소입니다");
    }

    @Test
    @DisplayName("학교 삭제")
    void removeSchool() {
        //given
        School school = createSchool();

        //when
        Long schoolId = schoolService.removeSchool(school.getId());

        //then
        Optional<School> findSchool = schoolRepository.findById(schoolId);
        assertThat(findSchool).isEmpty();
    }

    private School createSchool() {
        School school = new School("세종대학교", "본캠", SchoolType.UNIVERSITY, "02-3408-3114", "서울특별시 광진구 능동로 209", City.SEOUL, "http://www.sejong.ac.kr/");
        return schoolRepository.save(school);
    }
}