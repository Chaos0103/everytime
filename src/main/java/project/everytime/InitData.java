package project.everytime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.everytime.admin.school.School;
import project.everytime.admin.school.repository.SchoolRepository;
import project.everytime.client.board.Article;
import project.everytime.client.board.Board;
import project.everytime.client.board.BoardCategory;
import project.everytime.client.board.BoardType;
import project.everytime.client.board.repository.ArticleRepository;
import project.everytime.client.board.repository.BoardRepository;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static project.everytime.admin.school.City.*;
import static project.everytime.admin.school.SchoolType.*;

@Component
@RequiredArgsConstructor
public class InitData {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    @PostConstruct
    public void init() {
        List<School> schoolList = new ArrayList<>();
        schoolList.add(new School("서울대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("연세대학교 신촌캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("연세대학교 미래캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("고려대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("고려대학교 세종캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("서강대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("성균관대학교 인사캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("성균관대학교 자과캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한양대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한양대학교 ERICA캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("중앙대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("중앙대학교 안성캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("경희대학교 국제캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("경희대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한국외국어대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("한국외국어대학교 글로벌캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("서울시립대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("건국대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("건국대학교 GLOCAL캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("동국대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("동국대학교 WISE캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("홍익대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("홍익대학교 세종캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("국민대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("숭실대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("세종대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("단국대학교 죽전캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("단국대학교 천안캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("광운대학교", UNIVERSITY, SEOUL));
        schoolList.add(new School("명지대학교 인문캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("명지대학교 자연캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("상명대학교 서울캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("상명대학교 천안캠퍼스", UNIVERSITY, SEOUL));
        schoolList.add(new School("가톨릭대학교", UNIVERSITY, SEOUL));
        schoolRepository.saveAll(schoolList);

        School school = schoolRepository.findByName("세종대학교").get();
        User user = new User(school, "id", "pw!", "김밍깅", "20010101", "010-1234-5678", "밍술이", "2020", "F", "soju@soju.com", true);
        userRepository.save(user);

        List<Board> boards = new ArrayList<>();
        boards.add(new Board(school, BoardCategory.BASIC, "자유게시판", null, BoardType.LIST, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "비밀게시판", null, BoardType.ARTICLE, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "졸업생게시판", null, BoardType.ARTICLE, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "새내기게시판", null, BoardType.ARTICLE, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "시사·이슈", null, BoardType.LIST, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "장터게시판", null, BoardType.LIST, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "정보게시판", null, BoardType.LIST, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "홍보게시판", null, BoardType.LIST, 21, true, true, false));
        boards.add(new Board(school, BoardCategory.BASIC, "동아리·학회", null, BoardType.LIST, 21, true, true, false));
        boardRepository.saveAll(boards);

        Board board1 = boardRepository.findById(36L).get();
        Article article1 = new Article(user, board1, "술 마시고 싶다", "밍술이 등장!", 0, 0, 0, true, false);
        Board board2 = boardRepository.findById(37L).get();
        Article article2 = new Article(user, board2, "술 마시고 싶다", "밍술이 등장!", 0, 0, 0, true, false);
        for (int i = 0; i < 9; i++) {
            article2.addPosvote();
        }
        Article article3 = new Article(user, board2, "나는 말하는 감자다...", "코코박사님께 감사 인사를 드립니다ㅜㅜ", 0, 0, 0, true, false);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        article2.addPosvote();
        articleRepository.save(article2);
    }

}
