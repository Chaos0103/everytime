package project.everytime.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.school.School;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolRepositoryCustom {

    Optional<School> findByNameAndCampus(String name, String campus);

    Optional<School> findByTel(String tel);

    Optional<School> findByAddress(String address);

    Optional<School> findByUrl(String url);
}
