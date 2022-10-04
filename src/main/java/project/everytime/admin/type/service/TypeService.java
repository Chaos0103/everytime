package project.everytime.admin.type.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TypeService {

    Long addType(Long schoolId, String name);

    void editType(Long typeId, String name);

    void deleteType(Long typeId);
}
