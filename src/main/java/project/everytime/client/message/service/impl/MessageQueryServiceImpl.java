package project.everytime.client.message.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.message.Box;
import project.everytime.client.message.Message;
import project.everytime.client.message.api.BoxResponse;
import project.everytime.client.message.api.MessageResponse;
import project.everytime.client.message.repository.BoxRepository;
import project.everytime.client.message.repository.MessageRepository;
import project.everytime.client.message.service.MessageQueryService;
import project.everytime.client.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageQueryServiceImpl implements MessageQueryService {

    private final BoxRepository boxRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<BoxResponse> userMessageBoxes(Long userId) {
        List<Box> findBoxes = boxRepository.findBoxes(userId);
        return findBoxes.stream()
                .map(box -> new BoxResponse(userId, box))
                .toList();
    }

    @Override
    public List<MessageResponse> messagesByBoxId(Long boxId, Long userId) {

        List<Message> messages = messageRepository.findByBoxId(boxId);

        List<MessageResponse> result = new ArrayList<>();
        for (Message message : messages) {
            int type = getType(message.getUser(), userId);
            result.add(new MessageResponse(message.getId(), type, message.getText(), message.getCreatedDate()));
        }

        return result;
    }

    @Override
    public List<MessageResponse> messagesByItemId(Long itemId, Long userId) {
        List<Message> messages = messageRepository.findByItemId(itemId, userId);

        if (messages.isEmpty()) {
            return new ArrayList<>();
        }

        List<MessageResponse> result = new ArrayList<>();
        for (Message message : messages) {
            int type = getType(message.getUser(), userId);
            result.add(new MessageResponse(message.getId(), type, message.getText(), message.getCreatedDate()));
        }

        return result;
    }

    private int getType(User user, Long userId) {
        if (user == null) {
            return 0;
        } else if (user.getId().equals(userId)) {
            return 2;
        } else {
            return 1;
        }
    }
}
