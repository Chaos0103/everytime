package project.everytime.client.friend.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FriendService {

    //친구등록
    Long addFriend(Long userId, Long targetId);

    //친구삭제
    void deleteFriend(Long friendId);
}
