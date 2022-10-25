package project.everytime.client.message.api;

import lombok.Data;
import project.everytime.client.message.Box;

@Data
public class BoxResponse {

    private Long id;
    private String nickname;
    private int unreadCount;
    private String lastMessageText;
    private String lastMessageCreatedDate;

    public BoxResponse(Long userId, Box box) {
        this.id = box.getId();
        this.nickname = "익명";
        this.unreadCount = box.getLastSender().getId().equals(userId) ? 0 : box.getUnreadCount();
        this.lastMessageText = box.getLastMessageText();
        this.lastMessageCreatedDate = box.getLastMessageCreatedDate();
    }
}