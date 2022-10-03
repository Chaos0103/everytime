package project.everytime.client.board.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.board.Board;

import java.util.List;

@Transactional(readOnly = true)
public interface BoardQueryService {

    List<Board> findBoards(Long schoolId);
}
