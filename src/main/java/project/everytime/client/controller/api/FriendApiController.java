package project.everytime.client.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.everytime.client.friend.Friend;
import project.everytime.client.friend.service.FriendQueryService;
import project.everytime.client.friend.service.FriendService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.login.Login;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FriendApiController {

    private final FriendService friendService;
    private final FriendQueryService friendQueryService;

    @PostMapping("/find/friend/list")
    public FriendResponse friendList(@Login LoginUser loginUser) {
        List<Friend> friends = friendQueryService.findFriends(loginUser.getId());
        FriendResponse response = new FriendResponse();
        for (Friend friend : friends) {
            if (friend.isAccept()) {
                FriendDto friendDto = new FriendDto(friend.getId(), friend.getTarget().getId(), friend.getName());
                response.getFriends().add(friendDto);
            } else {
                FriendRequest request = new FriendRequest(friend.getId(), friend.getTarget().getId(), friend.getName());
                response.getRequests().add(request);
            }
        }
        return response;
    }

    @PostMapping("/update/friend/request/acceptance")
    public AcceptRequest accept(@RequestBody AcceptRequest request, @Login LoginUser loginUser) {
        log.debug("request={}", request);

        if (request.getAccept() == 1) {
            //수락
            friendService.acceptFriend(request.getId(), loginUser.getUsername());
        } else if (request.getAccept() == -1) {
            //거절
            friendService.rejectFriend(request.getId());
        }
        return request;
    }

    @PostMapping("/save/friend/request")
    public Integer requestFriend(@RequestBody SearchRequest request, @Login LoginUser loginUser) {
        //1: 성공, -1: 아이디 잘못, -2: 이미 친구, -3: 이미 요청한 상대,
        return friendService.requestFriend(loginUser.getId(), request.getData());
    }

    @Data
    static class FriendResponse {
        private List<FriendDto> friends = new ArrayList<>();
        private List<FriendRequest> requests = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    static class FriendDto {
        private Long id;
        private Long targetId;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class FriendRequest {
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
}
