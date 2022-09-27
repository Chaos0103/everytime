package project.everytime.admin.school.dto;

import lombok.Data;
import project.everytime.admin.school.City;
import project.everytime.admin.school.SchoolType;

@Data
public class SearchSchool {

    private String schoolName;
    private SchoolType type;
    private City city;
}
