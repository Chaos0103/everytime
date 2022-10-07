package project.everytime.client.friend.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.friend.Friend;

import java.util.List;

@Transactional(readOnly = true)
public interface FriendQueryService {

    //친구 목록 조회
    List<Friend> findFriends(Long userId);
}
