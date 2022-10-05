package project.everytime.admin.subject;

import lombok.Data;

@Data
public class SubjectAddDto {

    private String code;
    private String year;
    private String semester;
    private Integer grade;
    private String time;
    private String place;
    private Integer credit;
    private int popular;
    private String target;
    private String notice;
}
