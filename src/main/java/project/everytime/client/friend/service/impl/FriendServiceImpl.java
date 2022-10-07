package project.everytime.client.friend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everytime.client.ban.Ban;
import project.everytime.client.ban.repository.BanRepository;
import project.everytime.client.friend.Friend;
import project.everytime.client.friend.repository.FriendRepository;
import project.everytime.client.friend.service.FriendService;
import project.everytime.client.user.User;
import project.everytime.client.user.repository.UserRepository;
import project.everytime.exception.BlockedUserException;
import project.everytime.exception.DuplicateException;
import project.everytime.exception.NoSuchException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final BanRepository banRepository;

    @Override
    public Long requestFriend(Long userId, String data) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new NoSuchException("등록되지 않은 사용자");
        }

        Optional<User> findTargetUser = userRepository.findByEmailOrLoginId(data);
        if (findTargetUser.isEmpty()) {
            throw new NoSuchException("등록되지 않은 사용자");
        }

        Optional<Ban> findBan = banRepository.findBan(userId, findTargetUser.get().getId());
        if (findBan.isPresent()) {
            throw new BlockedUserException("차단된 사용자");
        }

        Optional<Friend> findFriend = friendRepository.alreadyFriend(userId, findTargetUser.get().getId());
        if (findFriend.isPresent()) {
            if (findFriend.get().isAccept()) {
                throw new DuplicateException("친구 등록 중복");
            } else {
                throw new DuplicateException("친구 요청 중복");
            }
        }
        //상대방에게 나를 등록
        Friend friend = new Friend(findTargetUser.get(), findUser.get(), findUser.get().getUsername());
        Friend savedFriend = friendRepository.save(friend);
        return savedFriend.getId();
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
    public void deleteFriend(Long userId, Long targetId) {
        Optional<Friend> friendOne = friendRepository.alreadyFriend(userId, targetId);
        friendOne.ifPresent(friendRepository::delete);
        Optional<Friend> friendTwo = friendRepository.alreadyFriend(targetId, userId);
        friendTwo.ifPresent(friendRepository::delete);
    }
}
