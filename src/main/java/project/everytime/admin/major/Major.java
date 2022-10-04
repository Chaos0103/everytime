package project.everytime.admin.major;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.TimeBaseEntity;
import project.everytime.admin.school.School;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "major_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false)
    private int order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Major parent;

    public Major(School school, String name, int order, Major parent) {
        this.school = school;
        this.name = name;
        this.order = order;
        this.parent = parent;
    }

    //== 비즈니스 로직 ==//
    public void edit(String name, int order, Major parent) {
        this.name = name;
        this.order = order;
        this.parent = parent;
    }
}
