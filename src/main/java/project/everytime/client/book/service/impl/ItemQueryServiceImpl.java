package project.everytime.client.book.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.book.Item;
import project.everytime.client.book.api.ItemListResponse;
import project.everytime.client.book.api.ItemViewResponse;
import project.everytime.client.book.repository.ItemRepository;
import project.everytime.client.book.service.ItemQueryService;
import project.everytime.exception.NoSuchException;

import java.util.List;
import java.util.Optional;

import static project.everytime.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;

    @Override
    public List<ItemListResponse> bookList(Long userId, Integer mine, String title, Integer start) {
        List<Item> items = itemRepository.findItemList(userId, mine, title, start);
        return items.stream()
                .map(ItemListResponse::new)
                .toList();
    }

    @Override
    public ItemViewResponse findItem(Long itemId, Long userId) {
        Item findItem = getItem(itemId);
        return new ItemViewResponse(findItem, userId);
    }

    private Item getItem(Long itemId) {
        Optional<Item> findItem = itemRepository.findItem(itemId);
        if (findItem.isEmpty()) {
            throw new NoSuchException(NO_SUCH_EXCEPTION_ITEM);
        }
        return findItem.get();
    }
}
