package project.everytime.admin.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.admin.lecture.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {
}
