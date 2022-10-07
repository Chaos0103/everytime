package project.everytime.client.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.client.friend.Friend;
import project.everytime.client.friend.service.FriendQueryService;
import project.everytime.client.friend.service.FriendService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.exception.BlockedUserException;
import project.everytime.exception.DuplicateException;
import project.everytime.exception.NoSuchException;
import project.everytime.login.Login;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendApiController {

    private final FriendService friendService;
    private final FriendQueryService friendQueryService;

    @PostMapping("/list")
    public FriendListResponse friendList(@Login LoginUser loginUser) {
        List<Friend> friends = friendQueryService.findFriends(loginUser.getId());

        FriendListResponse response = new FriendListResponse();
        friends.forEach(friend -> {
            FriendDto friendDto = new FriendDto(friend.getId(), friend.getTarget().getId(), friend.getName());
            if (friend.isAccept()) {
                response.getFriends().add(friendDto);
            } else {
                response.getRequests().add(friendDto);
            }
        });

        return response;
    }

    @PostMapping("/request/accept")
    public Integer accept(@RequestBody AcceptRequest request, @Login LoginUser loginUser) {
        log.debug("request={}", request);

        if (request.getAccept() == 1) {
            friendService.acceptFriend(request.getId(), loginUser.getUsername());
        } else if (request.getAccept() == -1) {
            friendService.rejectFriend(request.getId());
        }
        return request.getAccept();
    }

    @PostMapping("/request")
    public Integer requestFriend(@RequestBody SearchRequest request, @Login LoginUser loginUser) {
        try {
            Long friendId = friendService.requestFriend(loginUser.getId(), request.getData());
            log.debug("friendId = {}", friendId);
            return 1;
        } catch (NoSuchException exception) {
            log.debug(exception.getMessage());
            return -1;
        } catch (DuplicateException exception) {
            log.debug(exception.getMessage());
            if (exception.getMessage().equals("친구 등록 중복")) {
                return -2;
            } else {
                return -3;
            }
        } catch (BlockedUserException exception) {
            log.debug(exception.getMessage());
            return -4;
        }
    }

    @PostMapping("/remove")
    public void removeFriend(@Login LoginUser loginUser, @RequestBody RemoveRequest request) {
        friendService.deleteFriend(loginUser.getId(), request.getId());
    }

    @Data
    static class FriendListResponse {
        private List<FriendDto> friends = new ArrayList<>();
        private List<FriendDto> requests = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    static class FriendDto {
        private Long id;
        private Long targetId;
        private String name;
    }

    @Data
    static class AcceptRequest {
        private Long id;
        private int accept;
    }

    @Data
    static class SearchRequest {
        private String data;
    }

    @Data
    static class RemoveRequest {
        private Long id;
    }
}
