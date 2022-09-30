package project.everytime.admin.school.dto;

import lombok.Data;
import project.everytime.admin.school.City;
import project.everytime.admin.school.SchoolType;

@Data
public class SchoolSearchCondition {

    private String name;
    private SchoolType type;
    private City city;
}
