package project.everytime.admin.school.dto;

import lombok.Data;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;

@Data
public class SchoolResponse {

    private Long id;
    private String name;
    private SchoolType type;
    private City city;
    private int count;

    public SchoolResponse(School school) {
        this.id = school.getId();
        this.name = school.getName();
        this.type = school.getType();
        this.city = school.getCity();
        this.count = school.getCount();
    }
}
