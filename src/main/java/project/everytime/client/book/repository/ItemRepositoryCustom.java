package project.everytime.client.book.repository;

import project.everytime.client.book.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> findItemList(Long userId, Integer mine, String title, Integer start);
}
