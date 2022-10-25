package project.everytime.client.message;

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
public class Message extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    @Lob
    private String text;

    public Message(User user, Box box, String text) {
        this.user = user;
        this.box = box;
        this.text = text;
    }
}
