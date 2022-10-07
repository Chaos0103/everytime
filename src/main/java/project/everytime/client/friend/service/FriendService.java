package project.everytime.client.friend.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FriendService {

    //친구요청
    Long requestFriend(Long userId, String data);

    //친구수락
    void acceptFriend(Long friendId, String name);

    //친구거절
    void rejectFriend(Long friendId);

    //친구삭제
    void deleteFriend(Long userId, Long targetId);
}
