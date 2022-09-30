package project.everytime.admin.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.everytime.admin.school.City;
import project.everytime.admin.school.SchoolType;

@Data
@AllArgsConstructor
public class SchoolEdit {
    private String name;
    private String campus;
    private SchoolType type;
    private String tel;
    private String address;
    private City city;
    private String url;
}
