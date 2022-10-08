package project.everytime.client.board.dto;

import lombok.Data;
import project.everytime.client.board.Attach;

@Data
public class AttachResponse {
    private String fileName;

    public AttachResponse(Attach attach) {
        this.fileName = attach.getUploadFile().getStoreFileName();
    }
}
