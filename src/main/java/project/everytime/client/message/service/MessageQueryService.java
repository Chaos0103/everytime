package project.everytime.client.message.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.message.api.BoxResponse;
import project.everytime.client.message.api.MessageResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface MessageQueryService {

    List<BoxResponse> userMessageBoxes(Long userId);

    List<MessageResponse> messagesByBoxId(Long boxId, Long userId);

    List<MessageResponse> messagesByItemId(Long itemId, Long userId);
    //쪽지함 조회
    //대화내용조회
}
