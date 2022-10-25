package project.everytime.client.message.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MessageService {

    //새메세지 보내기
    //메세지 보내기
    Long sendMessageByBoxId(Long userId, Long boxId, String text);

    Long sendMessageByItemId(Long userId, Long itemId, String text);

    void readMessage(Long userId, Long boxId);

    //대화내용 삭제
}
