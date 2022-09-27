package project.everytime.admin.school;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.TimeBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "school_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, length = 50)
    private String schoolName;
    @Column(nullable = false, length = 10)
    private String campus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType type;
    @Column(nullable = false, unique = true, length = 13)
    private String tel;
    @Column(unique = true, nullable = false)
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;
    @Column(nullable = false, unique = true)
    private String url;
    @Column(nullable = false)
    private int count;

    public School(String schoolName, String campus, SchoolType type, String tel, String address, City city, String url) {
        this.schoolName = schoolName;
        this.campus = campus;
        this.type = type;
        this.tel = tel;
        this.address = address;
        this.city = city;
        this.url = url;
    }

    //==비즈니스 로직==//
    public void update(String schoolName, String campus, SchoolType type, String tel, String address, City city, String url) {
        this.schoolName = schoolName;
        this.campus = campus;
        this.type = type;
        this.tel = tel;
        this.address = address;
        this.city = city;
        this.url = url;
    }

    public void addCount() {
        this.count++;
    }

    public void removeCount() {
        this.count--;
    }
}
