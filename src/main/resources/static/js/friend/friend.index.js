$().ready(function () {
    var $container = $('#container');
    var $search, $friendrequestlist, $friendlist;
    var _fn = {
        initiate: function () {
            $search = $container.find('form.search');
            $friendrequestlist = $container.find('div.friendrequestlist');
            $friendlist = $container.find('div.friendlist');
            $friendrequestlist.on('click', 'a.friend > ul.buttons > li.accept', function () {
                var $a = $(this).parent().parent();
                _gfn.acceptFriendRequest($a.data('id'), 1);
                $a.remove();
                return false;
            }).on('click', 'a > ul.buttons > li.reject', function () {
                var $a = $(this).parent().parent();
                _gfn.acceptFriendRequest($a.data('id'), -1);
                $a.remove();
                return false;
            });
            _fn.loadFriendList();
        },
        loadFriendList: function () {
            _fn.ajaxFriendList(function (data) {
                if (!data) {
                    return false;
                }
                _fn.createFriendList(data);
            });
        },
        ajaxFriendList: function (callback) {
            $.ajax({
                url: '/find/friend/list',
                type: 'POST',
                success: function (data) {
                    callback(data);
                }
            });
        },
        createFriendList: function (data) {
            let requests = data.requests;
            for (let i = 0; i < requests.length; i++) {
                var $a = $('<a></a>').attr('href', '/friends/' + requests[i].targetId).addClass('friend').data('id', requests[i].id).appendTo($friendrequestlist);
                var $h3 = $('<h3></h3>').text('님이 친구 요청을 보냈습니다.').appendTo($a);
                $('<em></em>').text(requests[i].name).prependTo($h3);
                var $buttons = $('<ul></ul>').addClass('buttons').appendTo($a);
                $('<li></li>').text('수락').addClass('accept highlight').appendTo($buttons);
                $('<li></li>').text('거절').addClass('reject').appendTo($buttons);
                $('<hr>').appendTo($a);
                $friendrequestlist.show();
            }
            let friends = data.friends;
            for (let i = 0; i < friends.length; i++) {
                var $a = $('<a></a>').attr('href', '/friends/' + friends[i].targetId).addClass('friend').appendTo($friendlist);
                $('<h3></h3>').text(friends[i].name).appendTo($a);
            }
            if (!$friendlist.find('a.friend').length) {
                $('<div></div>').html('친구를 추가하면 친구의 시간표를 확인할 수 있어요!').addClass('dialog').appendTo($friendlist);
            }
        }
    };
    _fn.initiate();
});