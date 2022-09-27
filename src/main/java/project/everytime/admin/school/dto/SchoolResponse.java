package project.everytime.admin.school.dto;

import lombok.Data;
import project.everytime.admin.school.City;
import project.everytime.admin.school.School;
import project.everytime.admin.school.SchoolType;

@Data
public class SchoolResponse {

    private Long id;
    private String schoolName;
    private String campus;
    private SchoolType type;
    private String tel;
    private String address;
    private City city;
    private String url;
    private int count;

    public SchoolResponse(School school) {
        this.id = school.getId();
        this.schoolName = school.getSchoolName();
        this.campus = school.getCampus();
        this.type = school.getType();
        this.tel = school.getTel();
        this.address = school.getAddress();
        this.city = school.getCity();
        this.url = school.getUrl();
        this.count = school.getCount();
    }
}
