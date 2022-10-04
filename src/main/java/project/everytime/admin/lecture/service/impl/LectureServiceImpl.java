package project.everytime.admin.lecture.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.repository.LectureRepository;
import project.everytime.admin.lecture.service.LectureService;
import project.everytime.admin.school.School;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.exception.NoSuchException;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public Long addLecture(Long schoolId, String name, String professor) {

        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new NoSuchException();
        }

        Lecture savedLecture = lectureRepository.save(new Lecture(findSchool, name, professor));
        return savedLecture.getId();
    }

    @Override
    public void deleteLecture(Long lectureId) {
        Lecture findLecture = lectureRepository.findById(lectureId).orElse(null);
        if (findLecture == null) {
            throw new NoSuchException();
        }
        lectureRepository.delete(findLecture);
    }
}
