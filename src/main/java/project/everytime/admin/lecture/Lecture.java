package project.everytime.admin.lecture;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.school.School;
import project.everytime.client.TimeBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "lecture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(updatable = false, nullable = false)
    private String name;
    @Column(updatable = false, nullable = false, length = 50)
    private String professor;
    @Column(nullable = false)
    private float rate;

    public Lecture(School school, String name, String professor) {
        this.school = school;
        this.name = name;
        this.professor = professor;
        this.rate = 0.0f;
    }

    //== 비즈니스 로직 ==//
    public void updateRate(float rate) {
        this.rate = rate;
    }
}
