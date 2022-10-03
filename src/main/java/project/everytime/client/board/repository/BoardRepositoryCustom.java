package project.everytime.client.board.repository;

import project.everytime.client.board.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> findBySchoolId(Long schoolId);
}
