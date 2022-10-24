package project.everytime.client.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.client.book.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
