package project.everytime.client.board.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.board.dto.ArticleDto;

import java.io.IOException;

@Transactional
public interface ArticleService {

    //게시물 작성
    Long addArticle(Long userId, Long boardId, ArticleDto addArticle) throws IOException;

    //게시물 수정
    void editArticle(Long userId, Long articleId, ArticleDto editArticle) throws IOException;

    //게시물 삭제
    void deleteArticle(Long userId, Long articleId);
}
