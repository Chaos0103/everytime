package project.everytime.client.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.FileStore;
import project.everytime.client.UploadFile;
import project.everytime.client.board.Article;
import project.everytime.client.board.Board;
import project.everytime.client.board.dto.ArticleDto;
import project.everytime.client.board.repository.ArticleRepository;
import project.everytime.client.board.repository.BoardRepository;
import project.everytime.client.board.service.ArticleService;
import project.everytime.client.user.AccountStatus;
import project.everytime.client.user.AuthType;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.exception.NoSuchException;
import project.everytime.exception.NonAuthUserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    @Override
    public Long addArticle(Long userId, Long boardId, ArticleDto addArticle) throws IOException {

        User findUser = getUser(userId);
        //이용 정지 사용자 여부 or 학교 인증 여부
        if (isBan(findUser.getStatus())) {
            throw new NonAuthUserException("이용 제한된 사용자입니다");
        } else if (!isSchoolAuth(findUser.getAuthType())) {
            throw new NonAuthUserException("인증되지 않는 사용자입니다");
        }

        Board findBoard = getBoard(boardId);
        //유저의 학교가 맞는지 여부
        if (!isUserSchool(findUser.getSchool().getId(), findBoard.getSchool().getId())) {
            throw new NonAuthUserException("타학교로 등록된 사용자입니다");
        }

        List<UploadFile> uploadFiles = getUploadFiles(addArticle.getFiles());

        Article createArticle = Article.createArticle(findUser, findBoard, addArticle.getTitle(), addArticle.getText(), addArticle.isAnonymous(), addArticle.isQuestion(), uploadFiles);
        
        Article savedArticle = articleRepository.save(createArticle);

        return savedArticle.getId();
    }

    @Override
    public void editArticle(Long userId, Long articleId, ArticleDto editArticle) throws IOException {

        User findUser = getUser(userId);

        //이용 정지 사용자 여부 or 학교 인증 여부
        if (isBan(findUser.getStatus()) || !isSchoolAuth(findUser.getAuthType())) {
            throw new NonAuthUserException();
        }

        Article findArticle = getArticle(articleId);

        //작성자 본인 여부
        if (!isMyArticle(userId, findArticle.getUser().getId())) {
            throw new NonAuthUserException();
        }

        List<UploadFile> uploadFiles = getUploadFiles(editArticle.getFiles());

        findArticle.editArticle(editArticle.getTitle(), editArticle.getText(), editArticle.isAnonymous(), editArticle.isQuestion(), uploadFiles);
    }

    @Override
    public void deleteArticle(Long userId, Long articleId) {

        Article findArticle = getArticle(articleId);

        //작성자 본인 여부
        if (!isMyArticle(userId, findArticle.getUser().getId())) {
            throw new NonAuthUserException();
        }

        articleRepository.delete(findArticle);
    }

    private Article getArticle(Long articleId) {
        Article findArticle = articleRepository.findById(articleId).orElse(null);
        if (findArticle == null) {
            throw new NoSuchException();
        }
        return findArticle;
    }

    private User getUser(Long userId) {
        User findUser = userRepository.findById(userId).orElse(null);
        if (findUser == null) {
            throw new NoSuchException("등록되지 않은 회원입니다");
        }
        return findUser;
    }

    private Board getBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElse(null);
        if (findBoard == null) {
            throw new NoSuchException("존재하지 않는 게시판입니다");
        }
        return findBoard;
    }

    private static boolean isSchoolAuth(AuthType authType) {
        return authType != AuthType.NONE;
    }

    private static boolean isBan(AccountStatus status) {
        return status == AccountStatus.BAN;
    }

    private List<UploadFile> getUploadFiles(List<MultipartFile> files) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadFile uploadFile = fileStore.storeFile(file);
            uploadFiles.add(uploadFile);
        }
        return uploadFiles;
    }

    private static boolean isUserSchool(Long userSchoolId, Long boardSchoolId) {
        return userSchoolId.equals(boardSchoolId);
    }

    private boolean isMyArticle(Long userId, Long writerId) {
        return userId.equals(writerId);
    }
}
