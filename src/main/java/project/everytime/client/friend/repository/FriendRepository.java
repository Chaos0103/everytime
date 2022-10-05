package project.everytime.client.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everytime.client.friend.Friend;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserId(Long userId);
}
