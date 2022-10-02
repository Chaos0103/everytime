package project.everytime.client.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.school.School;
import project.everytime.client.TimeBaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Enumerated(EnumType.STRING)
    private BoardCategory category;
    private String name;
    private String info;
    private BoardType type;
    private Integer layout; //default:11
    private boolean anonymous;
    private boolean question;
    private boolean needLogin;

    @Builder
    public Board(School school, BoardCategory category, String name, String info, BoardType type, Integer layout, boolean anonymous, boolean question, boolean needLogin) {
        this.school = school;
        this.category = category;
        this.name = name;
        this.info = info;
        this.type = type;
        this.layout = layout;
        this.anonymous = anonymous;
        this.question = question;
        this.needLogin = needLogin;
    }
}
