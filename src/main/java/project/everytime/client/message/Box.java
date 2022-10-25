package project.everytime.client.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.client.TimeBaseEntity;
import project.everytime.client.book.Item;
import project.everytime.client.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Box extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "box_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User lastSender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "target_id")
    private User target;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int unreadCount;
    private String lastMessageText;
    private String lastMessageCreatedDate;

    public Box(User lastSender, User target, Item item) {
        this.lastSender = lastSender;
        this.target = target;
        this.item = item;
    }

    //== 비즈니스 로직==//
    public void addUnreadCount() {
        this.unreadCount++;
    }

    public void clearUnreadCount() {
        this.unreadCount = 0;
    }

    public void changeLastMessageText(User lastSender, String text, LocalDateTime createdDate) {
        if (!this.lastSender.getId().equals(lastSender.getId())) {
            this.target = this.lastSender;
            this.lastSender = lastSender;
        }
        this.lastMessageText = text;
        this.lastMessageCreatedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
        addUnreadCount();
    }
}
