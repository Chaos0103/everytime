if (!_gfn) var _gfn = {};
_gfn = _.extend(_gfn, {
    acceptFriendRequest: function (id, isAccept) {
        if (!id || !isAccept) {
            return false;
        }
        _gfn.ajaxAcceptFriendRequest(id, isAccept, function (data) {
            if (data === 1) {
                alert('친구 요청을 수락하였습니다.');
            } else if (data === -1) {
                alert('친구 요청을 거절하였습니다.');
            }
        });
    },
    ajaxAcceptFriendRequest: function (id, isAccept, callback) {
        $.ajax({
            url: '/update/friend/request/acceptance',
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            type: 'POST',
            data: JSON.stringify({
                'id': id,
                'accept': isAccept
            }),
            success: function (data) {
                var returns = Number(data.accept);
                callback(returns);
            }
        });
    }
});