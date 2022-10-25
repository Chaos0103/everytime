package project.everytime.client.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import project.everytime.client.message.api.BoxResponse;
import project.everytime.client.message.api.MessageResponse;
import project.everytime.client.message.service.MessageQueryService;
import project.everytime.client.message.service.MessageService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.login.Login;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageApiController {

    private final MessageService messageService;
    private final MessageQueryService messageQueryService;

    @GetMapping("/find/messages")
    public Result<List<MessageResponse>> messages(
            @RequestParam(value = "itemId", required = false) Long itemId,
            @RequestParam(value = "boxId", required = false) Long boxId,
            @Login LoginUser loginUser) {
        log.debug("itemId={}, boxId={}", itemId, boxId);

//        messageService.readMessage(loginUser.getId(), boxId);

        List<MessageResponse> messages;
        if (boxId != null) {
            messages = messageQueryService.messagesByBoxId(boxId, loginUser.getId());
        } else {
            messages = messageQueryService.messagesByItemId(itemId, loginUser.getId());
        }

        return new Result<>(messages);
    }

    @GetMapping("/find/messageboxes")
    public Result<List<BoxResponse>> messageboxes(@Login LoginUser loginUser) {
        List<BoxResponse> boxResponses = messageQueryService.userMessageBoxes(loginUser.getId());
        return new Result<>(boxResponses);
    }

    @PostMapping("/save/message")
    public Long sendMessage(@RequestBody MessageRequest request, @Login LoginUser loginUser) {
        log.debug(String.valueOf(request));

        if (!StringUtils.hasText(request.getText())) {
            return -2L;
        }

        Long messageId;
        if (request.getBoxId() != null) {
            messageId = messageService.sendMessageByBoxId(loginUser.getId(), request.getBoxId(), request.getText());
        } else {
            messageId = messageService.sendMessageByItemId(loginUser.getId(), request.getItemId(), request.getText());
        }
        return messageId;
    }
    /*
    if (responseCode === -1) {
                    alert('학교 인증 후 쪽지를 보낼 수 있습니다.');
                } else if (responseCode === -3) {
                    alert('올바르지 않은 쪽지 상대입니다.');
                } else if (responseCode === -4) {
                    alert('쪽지를 보낼 수 없는 상대입니다.');
                } else if (responseCode <= 0) {
                    alert('쪽지를 보낼 수 없습니다.');
                } else {
                    location.reload();
                }
     */

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class MessageRequest {
        private Long itemId;
        private Long boxId;
        private String text;
    }

}
