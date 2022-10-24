package project.everytime.client.book.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.book.api.ItemListResponse;
import project.everytime.client.book.api.ItemViewResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemQueryService {

    List<ItemListResponse> bookList(Long userId, Integer mine, String title, Integer start);

    ItemViewResponse findItem(Long bookId, Long userId);
}
