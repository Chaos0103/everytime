package project.everytime.admin.type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.type.Type;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findBySchoolIdAndName(Long schoolId, String name);

    List<Type> findBySchoolId(Long schoolId);
}
