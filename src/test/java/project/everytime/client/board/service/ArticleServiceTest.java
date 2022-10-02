package project.everytime.client.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.everytime.FileStore;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.client.board.Article;
import project.everytime.client.board.Board;
import project.everytime.client.board.dto.ArticleDto;
import project.everytime.client.board.repository.ArticleRepository;
import project.everytime.client.board.repository.BoardRepository;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.exception.NonAuthUserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static project.everytime.client.user.AccountStatus.*;
import static project.everytime.client.user.AuthType.*;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired ArticleService articleService;
    @Autowired ArticleRepository articleRepository;
    @Autowired SchoolRepository schoolRepository;
    @Autowired UserRepository userRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired FileStore fileStore;

    @Test
    @DisplayName("게시물 작성")
    void addArticle() throws IOException {
        //given
        School school = createSchool();
        User user = createUser(school, STUDENT, ACTIVE);
        Board board = createBoard(school);
        ArticleDto articleDto = new ArticleDto("title", "text", true, false, new ArrayList<>());

        //when
        Long articleId = articleService.addArticle(user.getId(), board.getId(), articleDto);

        //then
        Optional<Article> findArticle = articleRepository.findById(articleId);
        assertThat(findArticle).isPresent();
    }

    @Test
    @DisplayName("이용 정지 사용자 게시물 등록")
    void addArticleByBan() {
        //given
        School school = createSchool();
        User user = createUser(school, STUDENT, BAN);
        Board board = createBoard(school);
        ArticleDto articleDto = new ArticleDto("title", "text", true, false, new ArrayList<>());

        //when
        NonAuthUserException exception = assertThrows(NonAuthUserException.class,
                () -> articleService.addArticle(user.getId(), board.getId(), articleDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("이용 제한된 사용자입니다");
    }

    @Test
    @DisplayName("미인증 사용자 게시물 등록")
    void addArticleByAuth() {
        //given
        School school = createSchool();
        User user = createUser(school, NONE, ACTIVE);
        Board board = createBoard(school);
        ArticleDto articleDto = new ArticleDto("title", "text", true, false, new ArrayList<>());

        //when
        NonAuthUserException exception = assertThrows(NonAuthUserException.class,
                () -> articleService.addArticle(user.getId(), board.getId(), articleDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("인증되지 않는 사용자입니다");
    }

    @Test
    @DisplayName("타학교 게시물 등록")
    void addArticleByOtherSchool() {
        //given
        School school = createSchool();
        User user = createUser(school, STUDENT, ACTIVE);
        School otherSchool = schoolRepository.save(new School("건국대학교", SchoolType.UNIVERSITY, City.SEOUL));
        Board board = createBoard(otherSchool);
        ArticleDto articleDto = new ArticleDto("title", "text", true, false, new ArrayList<>());

        //when
        NonAuthUserException exception = assertThrows(NonAuthUserException.class,
                () -> articleService.addArticle(user.getId(), board.getId(), articleDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("타학교로 등록된 사용자입니다");
    }

    //== 편의 메서드 ==//
    private School createSchool() {
        return schoolRepository.save(new School("세종대학교", SchoolType.UNIVERSITY, City.SEOUL));
    }

    private User createUser(School school, AuthType authType, AccountStatus status) {
        User user = new User(school, "loginId", "password", "username", "20221002", "010-1234-5678", "nickname", "2022", "M", "email@email.com", true);
        user.changeAuthType(authType);
        user.changeStatus(status);
        return userRepository.save(user);
    }

    private Board createBoard(School school) {
        Board build = Board.builder().school(school).build();
        return boardRepository.save(build);
    }

}