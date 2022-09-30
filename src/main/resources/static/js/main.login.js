$().ready(function () {
    const $container = $('#container');
    const _set = {};
    const _fn = {
        initiate: function () {
            const $form = $container.find('form');
            const $loginId = $form.find('input[name="loginId"]');
            const $password = $form.find('input[name="password"]');
            $loginId.placeholder();
            $form.on('submit', function () {
                if (_set.isSubmitted === true) {
                    return false;
                }
                if (!$loginId.val().replace(/ /gi, '')) {
                    alert('아이디를 입력하세요!');
                    $loginId.focus();
                    return false;
                }
                if (!$password.val()) {
                    alert('비밀번호를 입력하세요!');
                    $password.focus();
                    return false;
                }
                _set.isSubmitted = true;
                setTimeout(function () {
                    _set.isSubmitted = false;
                }, 100);
            });
        }
    };
    _fn.initiate();
});