$().ready(function () {
    const $container = $('#container');
    const _fn = {
        initiate: function () {
            const $form = $container.find('form');
            const $email = $form.find('input[name="email"]');
            $email.placeholder();
            $form.on('submit', function () {
                if (!$email.val().replace(/ /gi, '')) {
                    alert('이메일을 입력하세요.');
                    $email.focus();
                    return false;
                }
            });
        }
    };
    _fn.initiate();
});