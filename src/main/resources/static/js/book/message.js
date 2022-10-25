bookstore.message = {
    sending: false,
    loadMessages: function ($messagesDiv) {
        const data = {};
        if (bookstore.data.itemId) {
            data.itemId = bookstore.data.itemId;
        } else if (bookstore.data.boxId) {
            data.boxId = bookstore.data.boxId;
        }
        bookstore.fn.findMessages(data, function (response) {
            const messages = response.data;
            if (!messages.length) {
                $('<div></div>').addClass('empty').text('아직 주고 받은 쪽지가 없습니다.').appendTo($messagesDiv);
                return false;
            }
            for (let i = 0; i < messages.length; i++) {
                const messageData = messages[i];
                const $message = $('<div></div>').addClass('message');
                if (messageData.type === 1) {
                    $message.addClass('received');
                    $('<span></span>').addClass('type').text('받은 쪽지').appendTo($message);
                } else if (messageData.type === 2) {
                    $message.addClass('sent');
                    $('<span></span>').addClass('type').text('보낸 쪽지').appendTo($message);
                } else {
                    $message.addClass('info');
                    $('<span></span>').addClass('type').text('안내').appendTo($message);
                }
                $('<time></time>').text(messageData.createdDate).appendTo($message);
                $('<hr>').appendTo($message);
                let text = messageData.text.replace(/(http:\/\/localhost:8080)(\/book\/view\/[a-z0-9]{1,})/gi, '<a href="$2">$1$2</a>');
                $('<p></p>').html(text).appendTo($message);
                $message.appendTo($messagesDiv);
            }
        });
    }
};

$().ready(function () {
    const $messagetextForm = $('#messagetext > form');
    const $messagesDiv = $('#messages');

    bookstore.data.itemId = $('#itemId').val();
    bookstore.data.boxId = $('#boxId').val();

    bookstore.message.loadMessages($messagesDiv);

    $messagetextForm
        .on('submit', function () {
            if (bookstore.message.sending === true) {
                return false;
            }
            const $textInput = $messagetextForm.find('input[name="text"]');
            const text = $textInput.val().trim();
            if (text.length < 2) {
                alert('내용을 두 글자 이상 입력하세요!');
                $textInput.focus();
                return false;
            }
            bookstore.message.sending = true;
            const data = {
                text: text
            };
            if (bookstore.data.itemId) {
                data.itemId = bookstore.data.itemId;
            } else if (bookstore.data.boxId) {
                data.boxId = bookstore.data.boxId;
            }
            bookstore.fn.saveMessage(data, function (response) {
                const responseCode = Number(response);
                if (responseCode === -1) {
                    alert('학교 인증 후 쪽지를 보낼 수 있습니다.');
                } else if (responseCode === -2) {
                    alert('내용을 입력해주세요!');
                } else if (responseCode === -3) {
                    alert('올바르지 않은 쪽지 상대입니다.');
                } else if (responseCode === -4) {
                    alert('쪽지를 보낼 수 없는 상대입니다.');
                } else if (responseCode <= 0) {
                    alert('쪽지를 보낼 수 없습니다.');
                } else {
                    location.reload();
                }
            });
            return false;
        })
        .on('click', 'div.sendbutton', function () {
            $messagetextForm.submit();
        });
});