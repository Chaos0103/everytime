package project.everytime.client.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.board.Board;
import project.everytime.client.board.repository.BoardRepository;
import project.everytime.client.board.service.BoardQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> findBoards(Long schoolId) {
        List<Board> boards = boardRepository.findBySchoolId(schoolId);
        return boards;
    }
}
