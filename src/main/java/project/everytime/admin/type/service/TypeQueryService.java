package project.everytime.admin.type.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.admin.type.dto.TypeResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface TypeQueryService {

    List<TypeResponse> findTypes(Long schoolId);
}
