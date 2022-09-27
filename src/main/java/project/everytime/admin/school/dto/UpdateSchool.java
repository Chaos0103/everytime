package project.everytime.admin.school.dto;

import lombok.Data;
import project.everytime.admin.school.City;
import project.everytime.admin.school.SchoolType;

@Data
public class UpdateSchool {
    private Long id;
    private String schoolName;
    private String campus;
    private SchoolType type;
    private String tel;
    private String address;
    private City city;
    private String url;
}
