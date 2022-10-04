package project.everytime.admin.lecture.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.dto.LectureSearchCondition;
import project.everytime.admin.lecture.repository.LectureRepository;
import project.everytime.admin.lecture.service.LectureQueryService;
import project.everytime.exception.NoSuchException;

@Service
@RequiredArgsConstructor
public class LectureQueryServiceImpl implements LectureQueryService {

    private final LectureRepository lectureRepository;

    @Override
    public Slice<Lecture> lectureSearch(Long schoolId, LectureSearchCondition condition, Pageable pageable) {
        return lectureRepository.findLecturesByCondition(schoolId, condition, pageable);
    }

    @Override
    public Lecture findLecture(Long lectureId) {
        Lecture findLecture = lectureRepository.findById(lectureId).orElse(null);
        if (findLecture == null) {
            throw new NoSuchException();
        }
        return findLecture;
    }
}
