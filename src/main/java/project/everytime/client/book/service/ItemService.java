package project.everytime.client.book.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.book.dto.BookAddDto;
import project.everytime.client.book.dto.ItemAddDto;

import java.io.IOException;

@Transactional
public interface ItemService {

    Long addItem(Long userId, Long lectureId, BookAddDto addBook, ItemAddDto addItem) throws IOException;

    Long editItemPrice(Long itemId, Integer price);

    Long editItemComment(Long itemId, String comment);

    Long soldOut(Long itemId);
}
