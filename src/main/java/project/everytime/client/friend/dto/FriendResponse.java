package project.everytime.client.friend.dto;

import lombok.Data;
import project.everytime.client.friend.Friend;

@Data
public class FriendResponse {

    private Long id;
    private Long targetId;
    private String name;

    public FriendResponse(Friend friend) {
        this.id = friend.getId();
        this.targetId = friend.getTarget().getId();
        this.name = friend.getName();
    }
}
