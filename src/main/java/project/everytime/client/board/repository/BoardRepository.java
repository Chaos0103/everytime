package project.everytime.client.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.client.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
