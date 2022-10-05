package project.everytime.admin.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.subject.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {
}
