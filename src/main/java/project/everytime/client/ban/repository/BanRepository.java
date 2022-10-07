package project.everytime.client.ban.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.everytime.client.ban.Ban;

import java.util.Optional;

public interface BanRepository extends JpaRepository<Ban, Long> {

    @Query("select b from Ban b where b.user.id = :targetId and b.target.id = :myId")
    Optional<Ban> findBan(@Param("myId") Long myId, @Param("targetId") Long targetId);
}
