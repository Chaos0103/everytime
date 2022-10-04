package project.everytime.admin.type.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.school.School;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.admin.type.Type;
import project.everytime.admin.type.repository.TypeRepository;
import project.everytime.admin.type.service.TypeService;
import project.everytime.exception.DuplicateException;
import project.everytime.exception.NoSuchException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final SchoolRepository schoolRepository;


    @Override
    public Long addType(Long schoolId, String name) {

        School findSchool = schoolRepository.findById(schoolId).orElse(null);
        if (findSchool == null) {
            throw new NoSuchException();
        }

        Optional<Type> findType = typeRepository.findBySchoolIdAndName(schoolId, name);
        if (findType.isPresent()) {
            throw new DuplicateException();
        }

        Type savedType = typeRepository.save(new Type(findSchool, name));

        return savedType.getId();
    }

    @Override
    public void editType(Long typeId, String name) {
        Type findType = typeRepository.findById(typeId).orElse(null);
        if (findType == null) {
            throw new NoSuchException();
        }
        findType.editName(name);
    }

    @Override
    public void deleteType(Long typeId) {
        Type findType = typeRepository.findById(typeId).orElse(null);
        if (findType == null) {
            throw new NoSuchException();
        }
        typeRepository.delete(findType);
    }
}
