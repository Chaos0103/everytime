package project.everytime.client.message.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.book.Item;
import project.everytime.client.book.repository.ItemRepository;
import project.everytime.client.message.Box;
import project.everytime.client.message.Message;
import project.everytime.client.message.repository.BoxRepository;
import project.everytime.client.message.repository.MessageRepository;
import project.everytime.client.message.service.MessageService;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.exception.NoSuchException;

import java.util.Optional;

import static project.everytime.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final BoxRepository boxRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public Long sendMessageByBoxId(Long userId, Long boxId, String text) {

        User sender = getUser(userId);
        Box box = getBox(boxId);

        Message message = new Message(sender, box, text);
        Message savedMessage = messageRepository.save(message);
        box.changeLastMessageText(sender, savedMessage.getText(), savedMessage.getCreatedDate());

        return savedMessage.getId();
    }

    @Override
    public Long sendMessageByItemId(Long userId, Long itemId, String text) {
        User sender = getUser(userId);
        Item findItem = getItem(itemId);

        Optional<Box> findBox = boxRepository.findBox(itemId, userId);

        Box savedBox;
        if (findBox.isEmpty()) {
            Box box = new Box(sender, findItem.getUser(), findItem);
            savedBox = boxRepository.save(box);
            String info = "[책방]에서 판매 중인 &quot;" + findItem.getBook().getTitle() + "&quot;에 대한 쪽지입니다.<br/>책 정보: http://localhost:8080/book/view/" + findItem.getId() + "<br/><br/>에브리타임은 이용자 간 합의한 거래에 대해 책임을 지지 않습니다. 허위 사실, 사기 등에 유의하시기 바랍니다.";
            Message message = new Message(null, box, info);
            messageRepository.save(message);
        } else {
            savedBox = findBox.get();
        }

        Message message = new Message(sender, savedBox, text);
        Message savedMessage = messageRepository.save(message);
        savedBox.changeLastMessageText(sender, savedMessage.getText(), savedMessage.getCreatedDate());

        return savedMessage.getId();
    }

    @Override
    public void readMessage(Long userId, Long boxId) {
        Box findBox = getBox(boxId);
        if (findBox.getTarget().getId().equals(userId)) {
            findBox.clearUnreadCount();
        }
    }

    private User getUser(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_USER);
        }
        return findUser.get();
    }

    private Box getBox(Long boxId) {
        Optional<Box> findBox = boxRepository.findById(boxId);
        if (findBox.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_BOX);
        }
        return findBox.get();
    }

    private Item getItem(Long itemId) {
        Optional<Item> findItem = itemRepository.findByIdWithBook(itemId);
        if (findItem.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_ITEM);
        }
        return findItem.get();
    }
}
