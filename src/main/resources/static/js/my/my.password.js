$().ready(function () {
    const $form = $('.container');
    const _fn = {
        init: function () {
            const $newPassword = $form.find('input[name="newPassword"]');
            const $checkNewPassword = $form.find('input[name="checkNewPassword"]');
            const $originPassword = $form.find('input[name="originPassword"]');
            $('.caution').hide();
            $form.on('submit', function () {
                if (!$originPassword.val()) {
                    alert('현재 비밀번호를 입력해주세요.');
                    $originPassword.focus();
                    return false;
                }
            });
            $newPassword.on('keyup', function () {
                if ($newPassword.val().length < 8) {
                    $('.newPassword').show();
                    console.log('8자 이상 입력하세요');
                } else {
                    $('.newPassword').hide();
                }
            });
            $checkNewPassword.on('keyup', function () {
                if ($newPassword.val() !== $checkNewPassword.val()) {
                    $('.checkNewPassword').show();
                    console.log('비밀번호가 일치하지 않습니다');
                } else {
                    $('.checkNewPassword').hide();
                    console.log('일치');
                }
            });
        }
    };
    _fn.init();
});