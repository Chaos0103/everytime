package project.everytime.client.friend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.admin.TimeBaseEntity;
import project.everytime.client.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "target_id")
    private User target;

    @Column(name = "friend_name", updatable = false, nullable = false, length = 50)
    private String name;

    public Friend(User user, User target, String name) {
        this.user = user;
        this.target = target;
        this.name = name;
    }
}
