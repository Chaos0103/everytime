$().ready(function () {
    const $container = $('#container');
    const $enterYear = $container.find('select[name="enterYear"]');
    const $schoolName = $container.find('input[name="schoolName"]');
    const $schoolId = $container.find('input[name="schoolId"]');
    const $schools = $container.find('ol.schools');
    const _set = {
        schools: []
    };
    const _fn = {
        init: function () {
            $.ajax({
                url: '/find/school/campus/list',
                type: 'POST',
                success: function (response) {
                    _set.schools = response.map(function (data) {
                        return {
                            id: data.id,
                            name: data.name,
                            lowerCaseName: data.name.toLowerCase()
                        };
                    });
                }
            });
            $schoolName.on('keyup', function (event) {
                _fn.onKeyUpSchoolName();
            }).on('focus', function () {
                _fn.onResetSchoolName();
            });
            $schools.on('click', 'li a', function () {
                _fn.onClickSchoolItem($(this).parent().data());
            });
            $container.on('submit', function (event) {
                _fn.onSubmit(event);
            });
        },
        onKeyUpSchoolName: function () {
            $schools.empty();
            const keyword = $schoolName.val();
            const lowerCaseKeyword = keyword.toLowerCase();
            let keywordForRegExp = new RegExp(lowerCaseKeyword);
            let matchedSchools = _.filter(_set.schools, function (school) {
                return school.lowerCaseName.match(keywordForRegExp);
            });
            if (keyword.length > 1 && matchedSchools.length === 0) {
                keywordForRegExp = new RegExp(lowerCaseKeyword.slice(0, -1));
                matchedSchools = _.filter(_set.schools, function (school) {
                    return school.lowerCaseName.match(keywordForRegExp);
                });
                if (matchedSchools.length === 0) {
                    const html = '검색된 학교가 없습니다.<br>에브리타임은 국내 ' + _set.schools.length + '개 대학을 지원합니다.';
                    $('<li></li>').html(html).addClass('empty').appendTo($schools);
                }
            }
            _.each(matchedSchools, function (school) {
                const html = '<a>' + school.name.replace(keyword, '<strong>' + keyword + '</strong>') + '</a>';
                $('<li></li>').html(html).data(school).appendTo($schools);
            });
        },
        onResetSchoolName: function () {
            if (!$schoolId.val()) {
                return;
            }
            $schoolName.val('');
            $schoolId.val('');
        },
        onClickSchoolItem: function (school) {
            $schoolName.val(school.name);
            $schoolId.val(school.id);
            $schools.empty();
        },
        onSubmit: function (event) {
            if (!$enterYear.val()) {
                alert('입학년도를 선택해주세요.');
                event.preventDefault();
                return;
            }
            if (!$schoolId.val()) {
                alert('가입할 학교를 선택해주세요.');
                event.preventDefault();
                return;
            }
        }
    };
    _fn.init();
});