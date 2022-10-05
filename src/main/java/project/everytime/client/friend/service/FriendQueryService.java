package project.everytime.client.friend.service;

import org.springframework.transaction.annotation.Transactional;
import project.everytime.client.friend.dto.FriendResponse;

import java.util.List;

@Transactional(readOnly = true)
public interface FriendQueryService {

    //친구 목록 조회
    List<FriendResponse> findFriends(Long userId);
}
