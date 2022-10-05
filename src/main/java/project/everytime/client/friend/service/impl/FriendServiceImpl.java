package project.everytime.client.friend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.friend.Friend;
import project.everytime.client.friend.repository.FriendRepository;
import project.everytime.client.friend.service.FriendService;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.exception.NoSuchException;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public Long addFriend(Long userId, Long targetId) {

        User findUser = userRepository.findById(userId).orElse(null);
        if (findUser == null) {
            throw new NoSuchException();
        }

        User findTarget = userRepository.findById(targetId).orElse(null);
        if (findTarget == null) {
            throw new NoSuchException();
        }

        Friend friend = new Friend(findUser, findTarget, findTarget.getUsername());
        Friend savedFriend = friendRepository.save(friend);

        return savedFriend.getId();
    }

    @Override
    public void deleteFriend(Long friendId) {
        Friend savedFriend = friendRepository.findById(friendId).orElse(null);
        if (savedFriend == null) {
            throw new NoSuchException();
        }
        friendRepository.delete(savedFriend);
    }
}
