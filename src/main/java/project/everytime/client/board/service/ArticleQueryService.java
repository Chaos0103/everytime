package project.everytime.client.board.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ArticleQueryService {
    //게시물 단건조회
}
