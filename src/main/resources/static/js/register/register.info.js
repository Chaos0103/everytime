$().ready(function () {
    const $container = $('#container');
    const $loginId = $container.find('input[name="loginId"]');
    const $password = $container.find('input[name="password"]');
    const $password2 = $container.find('input[name="password2"]');
    const $email = $container.find('input[name="email"]');
    const $nickname = $container.find('input[name="nickname"]');
    const $enterYear = $container.find('input[name="enterYear"]');
    const $schoolId = $container.find('input[name="schoolId"]');

    //삭제예정
    const $username = $container.find('input[name="username"]');
    const $birth = $container.find('input[name="birth"]');
    const $sex = $container.find('input[name="sex"]');
    const $phone = $container.find('input[name="phone"]');
    //삭제예정
    const _set = {
        isSubmitted: false
    };
    const _fn = {
        init: function () {
            $loginId.on('change focus keyup', function () {
                _fn.validateUserid();
            });
            $password.on('change focus keyup', function () {
                _fn.validatePassword();
            });
            $password2.on('change focus keyup', function () {
                _fn.validatePassword2();
            });
            $email.on('change focus keyup', function () {
                _fn.validateEmail();
            });
            $nickname.on('change focus keyup', function () {
                _fn.validateNickname();
            });
            $container.on('submit', function () {
                _fn.onSubmit();
                return false;
            });
        },
        validateUserid: function () {
            const userid = $loginId.val();
            const $caution = $loginId.next('div.caution');
            if (userid.length < 4) {
                $loginId.addClass('caution').removeClass('pass');
                $caution.text('4자 이상 입력하세요').show();
            } else if (/^[a-z0-9]+$/.test(userid) === false) {
                $loginId.addClass('caution').removeClass('pass');
                $caution.text('영문, 숫자만 입력하세요').show();
            } else {
                $loginId.addClass('pass').removeClass('caution');
                $caution.hide();
            }
        },
        validatePassword: function () {
            const password = $password.val();
            const $caution = $password.next('div.caution');
            const isInvalidChars = [
                /[a-z]/i.test(password),
                /[0-9]/i.test(password),
                /[^a-z0-9]/i.test(password)
            ].filter(function (t) {
                return t;
            }).length < 2;
            if (password.length < 8) {
                $password.addClass('caution').removeClass('pass');
                $caution.text('8자 이상 입력하세요').show();
            } else if (isInvalidChars) {
                $password.addClass('caution').removeClass('pass');
                $caution.text('영문, 숫자, 특수문자를 2종류 이상 조합해주세요').show();
            } else {
                $password.addClass('pass').removeClass('caution');
                $caution.hide();
            }
        },
        validatePassword2: function () {
            const password2 = $password2.val();
            const password = $password.val();
            const $caution = $password2.next('div.caution');
            if (password2 !== password) {
                $password2.addClass('caution').removeClass('pass');
                $caution.text('비밀번호가 일치하지 않습니다').show();
            } else {
                $password2.addClass('pass').removeClass('caution');
                $caution.hide();
            }
        },
        validateEmail: function () {
            const email = $email.val();
            const $caution = $email.next('div.caution');
            if (/^((\w|[`~!$#%^&*\-={}|'./?])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/.test(email) === false) {
                $email.addClass('caution').removeClass('pass');
                $caution.text('올바른 이메일을 입력하세요').show();
            } else {
                $email.addClass('pass').removeClass('caution');
                $caution.hide();
            }
        },
        validateNickname: function () {
            const nickname = $nickname.val();
            const $caution = $nickname.next('div.caution');
            if (nickname.length < 2) {
                $nickname.addClass('caution').removeClass('pass');
                $caution.text('2자 이상 입력하세요').show();
            } else if (/\s/.test(nickname) === true) {
                $nickname.addClass('caution').removeClass('pass');
                $caution.text('공백을 포함할 수 없습니다').show();
            } else {
                $nickname.addClass('pass').removeClass('caution');
                $caution.hide();
            }
        },
        onSubmit: function () {
            if (_set.isSubmitted === true) {
                return;
            }
            if ($loginId.is(':not(.pass)') || $password.is(':not(.pass)') || $password2.is(':not(.pass)') || $email.is(':not(.pass)') || $nickname.is(':not(.pass)')) {
                alert('모든 정보를 올바르게 입력해주세요.');
                return;
            }
            _set.isSubmitted = true;

            const json = {
                loginId: $loginId.val(),
                password: $password.val(),
                email: $email.val(),
                nickname: $nickname.val(),
                enterYear: $enterYear.val(),
                schoolId: $schoolId.val(),
                agreementAd: $container.data('adagreement'),
                username: $username.val(),
                birth: $birth.val(),
                sex: $sex.val(),
                phone: $phone.val()
            }

            $.ajax({
                url: '/save/user',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                type: 'POST',
                data: JSON.stringify(json),
                success: function (data) {
                    _set.isSubmitted = false;
                    const responseCode = data
                    console.log(data)
                    console.log(responseCode)
                    if (responseCode === -1) {
                        alert('아이디를 입력하세요.');
                        $loginId.focus();
                    } else if (responseCode === -2) {
                        alert('아이디는 4~20자만 가능합니다.');
                        $loginId.focus();
                    } else if (responseCode === -3) {
                        alert('아이디는 영문, 숫자만 가능합니다.');
                        $loginId.focus();
                    } else if (responseCode === -4) {
                        alert('이미 등록된 아이디입니다.');
                        $loginId.focus();
                    } else if (responseCode === -5) {
                        alert('비밀번호를 입력하세요.');
                        $password.focus();
                    } else if (responseCode === -6) {
                        alert('비밀번호는 8~20자까지 가능합니다.');
                        $password.focus();
                    } else if (responseCode === -21) {
                        alert('비밀번호는 영문, 숫자, 특수문자를 2종류 이상 조합해주세요.');
                        $password.focus();
                    } else if (responseCode === -17) {
                        alert('아이디와 동일한 비밀번호는 이용할 수 없습니다.');
                    } else if (responseCode === -12) {
                        alert('이메일을 입력하세요.');
                        $email.focus();
                    } else if (responseCode === -18) {
                        alert('올바른 이메일을 입력하세요.');
                        $email.focus();
                    } else if (responseCode === -13) {
                        alert('이미 등록된 이메일입니다.');
                        $email.focus();
                    } else if (responseCode === -9) {
                        alert('닉네임을 입력하세요.');
                        $nickname.focus();
                    } else if (responseCode === -10) {
                        alert('닉네임은 10자 이내로 입력하세요.');
                        $nickname.focus();
                    } else if (responseCode === -16) {
                        alert('닉네임에 공백을 포함할 수 없습니다.');
                        $nickname.focus();
                    } else if (responseCode === -11) {
                        alert('이미 등록된 닉네임입니다.');
                        $nickname.focus();
                    } else if (responseCode === -7 || responseCode === -14 || responseCode === -15) {
                        alert('일부 정보가 존재하지 않습니다. 정상적인 회원가입 절차에 따라 다시 진행해주세요.');
                    } else if (responseCode === -19) {
                        alert('이미 가입된 계정이 있습니다. 아이디/비밀번호 찾기를 이용해주세요.');
                    } else if (responseCode === -20) {
                        alert('반복적인 탈퇴 및 재가입은 일정 기간 제한됩니다.');
                    } else if (responseCode === 0) {
                        alert('더 이상 회원가입을 진행할 수 없습니다. [문의하기]를 통해 문의해주시기 바랍니다.');
                    } else {
                        let message = '회원가입이 완료되었습니다.';
                        if ($container.data('adagreement') === 1) {
                            const date = new Date();
                            const adAgreementDate = date.getFullYear() + '년 ' + (date.getMonth() + 1) + '월 ' + date.getDate() + '일';
                            message += '\n\n에브리타임\n' + adAgreementDate + '\n광고성 정보 수신 동의 완료';
                        }
                        alert(message);
                        location.href = $container.data('redirecturl');
                    }
                }
            });
        }
    };
    _fn.init();
});