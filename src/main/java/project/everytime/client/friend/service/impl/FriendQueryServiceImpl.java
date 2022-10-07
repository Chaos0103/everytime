package project.everytime.client.friend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.friend.Friend;
import project.everytime.client.friend.repository.FriendRepository;
import project.everytime.client.friend.service.FriendQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendQueryServiceImpl implements FriendQueryService {

    private final FriendRepository friendRepository;


    @Override
    public List<Friend> findFriends(Long userId) {
        return friendRepository.findByUserId(userId);
    }
}
