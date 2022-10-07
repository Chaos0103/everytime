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
    public Integer requestFriend(Long userId, String data) {
        //1: 성공, -1: 아이디 잘못, -2: 이미 친구, -3: 이미 요청한 상대,
        User findUser = userRepository.findById(userId).orElse(null);
        if (findUser == null) {
            throw new NoSuchException();
        }

        User findTarget = userRepository.findByEmailOrLoginId(data).orElse(null);
        if (findTarget == null) {
            return -1;
        }

        Friend findFriend = friendRepository.alreadyFriend(userId, findTarget.getId()).orElse(null);
        if (findFriend == null) {
            Friend friend = new Friend(findTarget, findUser, findUser.getUsername());
            friendRepository.save(friend);
            return 1;
        } else if (findFriend.isAccept()) {
            return -2;
        } else {
            return -3;
        }
    }

    @Override
    public void acceptFriend(Long friendId, String name) {
        Friend findFriend = friendRepository.findById(friendId).orElse(null);
        if (findFriend == null) {
            throw new NoSuchException();
        }
        findFriend.friendAccept();

        Friend friend = new Friend(findFriend.getTarget(), findFriend.getUser(), name);
        friend.friendAccept();
        friendRepository.save(friend);
    }

    @Override
    public void rejectFriend(Long friendId) {
        Friend findFriend = friendRepository.findById(friendId).orElse(null);
        if (findFriend == null) {
            throw new NoSuchException();
        }
        friendRepository.delete(findFriend);
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
