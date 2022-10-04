package project.everytime.admin.type;

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
public class Type extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "type_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false, length = 10)
    private String name;

    public Type(School school, String name) {
        this.school = school;
        this.name = name;
    }

    //== 비즈니스 로직 ==//
    public void editName(String name) {
        this.name = name;
    }
}
