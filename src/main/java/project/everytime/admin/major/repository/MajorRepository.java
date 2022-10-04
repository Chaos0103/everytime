package project.everytime.admin.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.major.Major;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {

    List<Major> findBySchoolId(Long schoolId);
}
