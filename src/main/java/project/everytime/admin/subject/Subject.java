package project.everytime.admin.subject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.TimeBaseEntity;
import project.everytime.admin.lecture.Lecture;
import project.everytime.admin.major.Major;
import project.everytime.admin.subject.dto.TimeplaceAddDto;
import project.everytime.admin.timeplace.Timeplace;
import project.everytime.admin.type.Type;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(nullable = false, length = 20)
    private String code;
    @Column(updatable = false, nullable = false, length = 4)
    private String year;
    @Column(updatable = false, nullable = false, length = 1)
    private String semester;
    @Column(updatable = false, nullable = false)
    private String name;
    @Column(updatable = false, nullable = false, length = 50)
    private String professor;
    @Column(nullable = false)
    private Integer grade;
    @Column(length = 20)
    private String time;
    @Column(length = 20)
    private String place;
    @Column(nullable = false)
    private Integer credit;
    @Column(nullable = false)
    private int popular;
    @Column(nullable = false, length = 20)
    private String target;
    @Column(nullable = false, length = 20)
    private String notice;

    @OneToMany(mappedBy = "subject", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Timeplace> timeplaces = new ArrayList<>();

    public Subject(Major major, Type type, Lecture lecture, String code, String year, String semester, String name, String professor, Integer grade, String time, String place, Integer credit, String target, String notice) {
        this.major = major;
        this.type = type;
        this.lecture = lecture;
        this.code = code;
        this.year = year;
        this.semester = semester;
        this.name = name;
        this.professor = professor;
        this.grade = grade;
        this.time = time;
        this.place = place;
        this.credit = credit;
        this.popular = 0;
        this.target = target;
        this.notice = notice;
    }

    //== 생성 메서드 ==//
    public static Subject createSubject(Major major, Type type, Lecture lecture, String code, String year, String semester, String name, String professor, Integer grade, String time, String place, Integer credit, String target, String notice, List<TimeplaceAddDto> timeplaces) {
        Subject subject = new Subject(major, type, lecture, code, year, semester, name, professor, grade, time, place, credit, target, notice);
        for (TimeplaceAddDto dto : timeplaces) {
            Timeplace timeplace = new Timeplace(subject, dto.getDay(), dto.getStart(), dto.getEnd(), dto.getPlace());
            subject.addTimeplace(timeplace);
        }
        return subject;
    }

    //== 연관관계 편의 메서드 ==//
    public void addTimeplace(Timeplace timeplace) {
        this.timeplaces.add(timeplace);
    }

    //== 비즈니스 로직 ==//
    public void addPopular() {
        this.popular++;
    }

    public void removePopular() {
        int result = this.popular - 1;
        if (result < 0) {
            throw new IllegalArgumentException();
        }
        this.popular = result;
    }


}
