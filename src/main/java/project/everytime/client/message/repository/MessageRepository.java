package project.everytime.client.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.everytime.client.message.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.box.id = :boxId order by m.createdDate desc")
    List<Message> findByBoxId(@Param("boxId") Long boxId);

    @Query("select m from Message m join fetch m.box b where b.item.id = :itemId and (m.box.lastSender.id = :userId or m.box.target.id = :userId)  order by m.createdDate desc")
    List<Message> findByItemId(@Param("itemId") Long itemId, @Param("userId") Long userId);
}
