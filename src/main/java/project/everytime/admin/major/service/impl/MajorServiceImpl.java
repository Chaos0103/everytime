package project.everytime.admin.major.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.major.Major;
import project.everytime.admin.major.repository.MajorRepository;
import project.everytime.admin.major.service.MajorService;
import project.everytime.admin.school.School;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.exception.NoSuchException;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public Long addMajor(Long schoolId, Long parentId, String name, int order) {

        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new NoSuchException();
        }

        Major savedMajor;
        if (parentId == null) {
            savedMajor = majorRepository.save(new Major(findSchool, name, order, null));
        } else {
            Major findParent = majorRepository.findById(parentId).orElse(null);
            if (findParent == null) {
                throw new NoSuchException();
            }
            savedMajor = majorRepository.save(new Major(findSchool, name, order, findParent));
        }

        return savedMajor.getId();
    }

    @Override
    public void editMajor(Long majorId, Long parentId, String name, int order) {
        Major findMajor = majorRepository.findById(majorId).orElse(null);
        if (findMajor == null) {
            throw new NoSuchException();
        }

        if (parentId == null) {
            findMajor.edit(name, order, null);
        } else {
            Major findParent = majorRepository.findById(parentId).orElse(null);
            if (findParent == null) {
                throw new NoSuchException();
            }
            findMajor.edit(name, order, findParent);
        }
    }

    @Override
    public void deleteMajor(Long majorId) {
        Major findMajor = majorRepository.findById(majorId).orElse(null);
        if (findMajor == null) {
            throw new NoSuchException();
        }
        majorRepository.delete(findMajor);
    }
}
