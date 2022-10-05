package project.everytime.admin.subject.dto;

import lombok.Data;
import project.everytime.admin.subject.Subject;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectResponse {

    private Long subjectId;
    private String code;
    private String name;
    private String professor;
    private String type;
    private Integer grade;
    private String time;
    private String place;
    private Integer credit;
    private Integer popular;
    private String target;
    private String notice;
    private Long lectureId;
    private Float lectureRate;
    private String major;
    private List<TimeplaceResponse> timeplaces = new ArrayList<>();

    public SubjectResponse(Subject subject) {
        this.subjectId = subject.getId();
        this.code = subject.getCode();
        this.name = subject.getName();
        this.professor = subject.getProfessor();
        this.type = subject.getType().getName();
        this.grade = subject.getGrade();
        this.time = subject.getTime();
        this.place = subject.getPlace();
        this.credit = subject.getCredit();
        this.popular = subject.getPopular();
        this.target = subject.getTarget();
        this.notice = subject.getNotice();
        this.lectureId = subject.getLecture().getId();
        this.lectureRate = subject.getLecture().getRate();
        this.major = subject.getMajor().getName();
        this.timeplaces = subject.getTimeplaces().stream()
                .map(TimeplaceResponse::new)
                .toList();
    }
}
