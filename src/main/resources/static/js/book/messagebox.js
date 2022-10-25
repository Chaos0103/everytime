bookstore.messagebox = {
    loadMessageBoxes: function ($boxesDiv) {
        bookstore.fn.findMessageBoxes(function (response) {

            let boxes = [];
            for (let i = 0; i < response.data.length; i++) {
                let data = response.data[i];
                const input = {
                    id: data.id,
                    text: data.lastMessageText,
                    date: bookstore.fn.formatDate(data.lastMessageCreatedDate),
                    unreadCount: Number(data.unreadCount)
                    // updatedTime: Number($data.attr('updated_time'))
                }
                boxes.push(input);
            }

            boxes = _.sortBy(boxes, 'updatedTime').reverse();
            if (!boxes.length) {
                $('<a></a>').addClass('box empty').text('아직 주고 받은 쪽지가 없습니다.').appendTo($boxesDiv);
                return false;
            }
            _.each(boxes, function (box) {
                const $box = $('<a></a>').addClass('box').attr({
                    href: '/book/message/box/' + box.id
                });
                $('<time></time>').html(box.date).appendTo($box);
                if (box.unreadCount > 0) {
                    $('<span></span>').addClass('unread').html('+' + box.unreadCount).appendTo($box);
                }
                $('<hr>').appendTo($box);
                $('<p></p>').html(box.text).appendTo($box);
                $box.appendTo($boxesDiv);
            });
        });
    }
};

$().ready(function () {
    var $boxesDiv = $('#messageboxes');
    bookstore.messagebox.loadMessageBoxes($boxesDiv);
});