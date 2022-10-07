package project.everytime.client.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.everytime.client.friend.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserId(Long userId);

    @Query("select f from Friend f where f.user.id = :targetId and f.target.id = :userId")
    Optional<Friend> alreadyFriend(@Param("userId") Long userId, @Param("targetId") Long targetId);
}
