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
        duplicatedSchool(school.getName());

        School savedSchool = schoolRepository.save(school);

        return savedSchool.getId();
    }

    @Override
    public void editSchool(Long schoolId, SchoolEdit updateSchool) {
        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new IllegalArgumentException();
        }

        if (!findSchool.getName().equals(updateSchool.getName())) {
            duplicatedSchool(updateSchool.getName());
        }

        findSchool.update(updateSchool.getName(), updateSchool.getType(), updateSchool.getCity());
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

    private void duplicatedSchool(String schoolName) {
        Optional<School> findSchool = schoolRepository.findByName(schoolName);
        validation(findSchool, "이미 등록된 학교입니다");
    }

    private static void validation(Optional<School> findSchool, String message) {
        if (findSchool.isPresent()) {
            throw new DuplicateException(message);
        }
    }
}
