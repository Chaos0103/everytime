package project.everytime.admin.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.school.School;
import project.everytime.admin.school.dto.SchoolEdit;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.admin.school.service.SchoolService;
import project.everytime.exception.DuplicateException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public Long addSchool(School school) {
        duplicatedSchool(school.getName(), school.getCampus());
        duplicatedTel(school.getTel());
        duplicatedAddress(school.getAddress());
        duplicatedUrl(school.getUrl());

        School savedSchool = schoolRepository.save(school);

        return savedSchool.getId();
    }

    @Override
    public void editSchool(Long schoolId, SchoolEdit updateSchool) {
        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new IllegalArgumentException();
        }

        if (!findSchool.getName().equals(updateSchool.getSchoolName())
                || !findSchool.getCampus().equals(updateSchool.getCampus())) {
            duplicatedSchool(updateSchool.getSchoolName(), updateSchool.getCampus());
        }

        if (!findSchool.getTel().equals(updateSchool.getTel())) {
            duplicatedTel(updateSchool.getTel());
        }

        if (!findSchool.getAddress().equals(updateSchool.getAddress())) {
            duplicatedAddress(updateSchool.getAddress());
        }

        if (!findSchool.getUrl().equals(updateSchool.getUrl())) {
            duplicatedUrl(updateSchool.getUrl());
        }

        findSchool.update(updateSchool.getSchoolName(), updateSchool.getCampus(), updateSchool.getType(), updateSchool.getTel(), updateSchool.getAddress(), updateSchool.getCity(), updateSchool.getUrl());
    }

    @Override
    public Long removeSchool(Long schoolId) {
        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new IllegalArgumentException();
        }
        schoolRepository.delete(findSchool);
        return schoolId;
    }

    private void duplicatedUrl(String url) {
        Optional<School> findSchool = schoolRepository.findByUrl(url);
        validation(findSchool, "이미 등록된 홈페이지 주소입니다");
    }

    private void duplicatedAddress(String address) {
        Optional<School> findSchool = schoolRepository.findByAddress(address);
        validation(findSchool, "이미 등록된 주소입니다");
    }

    private void duplicatedTel(String tel) {
        Optional<School> findSchool = schoolRepository.findByTel(tel);
        validation(findSchool, "이미 등록된 연락처입니다");
    }

    private void duplicatedSchool(String schoolName, String campus) {
        Optional<School> findSchool = schoolRepository.findByNameAndCampus(schoolName, campus);
        validation(findSchool, "이미 등록된 학교입니다");
    }

    private static void validation(Optional<School> findSchool, String message) {
        if (findSchool.isPresent()) {
            throw new DuplicateException(message);
        }
    }
}
