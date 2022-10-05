package project.everytime.admin.subject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.lecture.repository.LectureRepository;
import project.everytime.admin.major.Major;
import project.everytime.admin.major.repository.MajorRepository;
import project.everytime.admin.subject.Subject;
import project.everytime.admin.subject.SubjectAddDto;
import project.everytime.admin.subject.dto.TimeplaceAddDto;
import project.everytime.admin.subject.repository.SubjectRepository;
import project.everytime.admin.subject.service.SubjectService;
import project.everytime.admin.type.Type;
import project.everytime.admin.type.repository.TypeRepository;
import project.everytime.exception.NoSuchException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final MajorRepository majorRepository;
    private final TypeRepository typeRepository;
    private final LectureRepository lectureRepository;

    @Override
    public Long addSubject(Long majorId, Long typeId, Long lectureId, SubjectAddDto subjectAddDto, List<TimeplaceAddDto> timeplaceAddDtos) {

        Major findMajor = majorRepository.findById(majorId).orElse(null);
        if (findMajor == null) {
            throw new NoSuchException();
        }

        Type findType = typeRepository.findById(typeId).orElse(null);
        if (findType == null) {
            throw new NoSuchException();
        }

        Lecture findLecture = lectureRepository.findById(lectureId).orElse(null);
        if (findLecture == null) {
            throw new NoSuchException();
        }

        Subject subject = Subject.createSubject(findMajor, findType, findLecture, subjectAddDto.getCode(), subjectAddDto.getYear(), subjectAddDto.getSemester(), findLecture.getName(), findLecture.getProfessor(), subjectAddDto.getGrade(), subjectAddDto.getTime(), subjectAddDto.getPlace(), subjectAddDto.getCredit(), subjectAddDto.getTarget(), subjectAddDto.getNotice(), timeplaceAddDtos);
        Subject savedSubject = subjectRepository.save(subject);

        return savedSubject.getId();
    }

}
