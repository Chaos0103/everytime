package project.everytime.client.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.board.Article;
import project.everytime.client.board.repository.ArticleRepository;
import project.everytime.client.board.service.ArticleQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleQueryServiceImpl implements ArticleQueryService {

    private final ArticleRepository articleRepository;


    @Override
    public List<Article> findRecent4(Long boardId) {
        return articleRepository.findTop4(boardId);
    }

    @Override
    public List<Article> findRecent2(Long boardId) {
        return articleRepository.findTop2(boardId);
    }

    @Override
    public List<Article> hotArticle(Long schoolId) {
        return articleRepository.findHotArticle(schoolId);
    }
}
