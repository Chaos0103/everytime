package project.everytime.client.book.repository;

import project.everytime.client.book.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {

    List<Item> findItemList(Long userId, Integer mine, String title, Integer start);

    Optional<Item> findItem(Long itemId);
}
