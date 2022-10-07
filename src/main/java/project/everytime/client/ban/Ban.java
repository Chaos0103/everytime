package project.everytime.client.ban;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ban extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "ban_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "target_id")
    private User target;

    public Ban(User user, User target) {
        this.user = user;
        this.target = target;
    }
}
