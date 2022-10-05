package project.everytime.admin.subject.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectSearchCondition {

    private Long majorId;
    private String name;
    private String professor;
    private String code;
    private String place;
    private SortType sortType;
    private List<Integer> grades = new ArrayList<>();
    private List<Long> types = new ArrayList<>();
    private List<Integer> credits = new ArrayList<>();
}
