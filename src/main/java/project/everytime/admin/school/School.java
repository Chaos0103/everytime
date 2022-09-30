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
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;
    @Column(nullable = false)
    private int count;

    public School(String name, SchoolType type, City city) {
        this.name = name;
        this.type = type;
        this.city = city;
    }

    //==비즈니스 로직==//
    public void update(String name, SchoolType type, City city) {
        this.name = name;
        this.type = type;
        this.city = city;
    }

    public void addCount() {
        this.count++;
    }

    public void removeCount() {
        this.count--;
    }
}
