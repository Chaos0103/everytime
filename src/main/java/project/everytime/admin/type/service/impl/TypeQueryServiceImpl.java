package project.everytime.admin.type.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.admin.type.Type;
import project.everytime.admin.type.dto.TypeResponse;
import project.everytime.admin.type.repository.TypeRepository;
import project.everytime.admin.type.service.TypeQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeQueryServiceImpl implements TypeQueryService {

    private final TypeRepository typeRepository;

    @Override
    public List<TypeResponse> findTypes(Long schoolId) {
        List<Type> findTypes = typeRepository.findBySchoolId(schoolId);
        return findTypes.stream()
                .map(TypeResponse::new)
                .toList();
    }
}
