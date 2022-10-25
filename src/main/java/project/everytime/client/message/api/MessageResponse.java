package project.everytime.client.message.api;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class MessageResponse {

    private Long id;
    private int type;
    private String text;
    private String createdDate;
//    type: 0:안내, 1: 받은쪽지, 2: 보낸쪽지

    public MessageResponse(Long id, int type, String text, LocalDateTime createdDate) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
    }
}
