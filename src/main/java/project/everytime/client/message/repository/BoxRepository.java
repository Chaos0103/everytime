package project.everytime.client.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.everytime.client.message.Box;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {

    @Query("select b from Box b where b.target.id = :userId or b.lastSender.id = :userId")
    List<Box> findBoxes(@Param("userId") Long userId);

    @Query("select b from Box b where b.item.id = :itemId and (b.lastSender.id = :userId or b.target.id = :userId)")
    Optional<Box> findBox(@Param("itemId") Long itemId, @Param("userId") Long userId);
}
