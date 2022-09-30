package project.everytime.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.school.School;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolRepositoryCustom {

    Optional<School> findByName(String name);
}
