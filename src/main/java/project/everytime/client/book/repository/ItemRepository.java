package project.everytime.client.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.everytime.client.book.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    @Query("select i from Item i join fetch i.book where i.id = :itemId")
    Optional<Item> findByIdWithBook(@Param("itemId") Long itemId);

}
