if (!_gfn) var _gfn = {};
_gfn = _.extend(_gfn, {
    createArticleItem: function ($target, data) {
        const $a = $('<a></a>').addClass('article').appendTo($target);
        if (data.boardId && data.id) {
            $a.attr('href', '/' + data.boardId + '/view/' + data.id);
        } else if (data.lectureId) {
            $a.attr('href', '/lecture/view/' + data.lectureId);
            var rate = Number(data.rate) / 5 * 100 + '%';
            var $star = $('<span></span>').addClass('star').appendTo($a);
            $('<span></span>').addClass('on').width(rate).appendTo($star);
        }
        var attachUrl;
        if (data.attachId) {
            const attachId = Number(data.attachId);
            if (attachId === -1) {
                attachUrl = '/images/attach.unauthorized.png';
            } else {
                var extension = data.attachFileName.split('.').pop().toUpperCase();
                var extensionsForImage = ['GIF', 'JPG', 'JPEG', 'PNG'];
                if (_.indexOf(extensionsForImage, extension) > -1) {
                    attachUrl = data.attachFileUrl;
                }
            }
        }
        if (attachUrl) {
            $('<img>').addClass('thumbnail').attr('src', attachUrl).appendTo($a);
        }
        if (data.title) {
            $('<p></p>').addClass('title').html(data.title).appendTo($a);
            $('<p></p>').addClass('small').html(data.text).appendTo($a);
        } else {
            $('<p></p>').html(data.text).appendTo($a);
        }
        if (data.boardName) {
            $('<h4></h4>').html(data.boardName).appendTo($a);
        } else if (data.createdDate) {
            $('<time></time>').text(_gfn.formatRelativeDate(data.createdDate)).appendTo($a);
        }
        if (data.posvote >= 0 || data.commentCount >= 0) {
            var $status = $('<ul></ul>').addClass('status').appendTo($a);
            $('<li></li>').addClass('vote active').text(data.posvote).appendTo($status);
            $('<li></li>').addClass('comment active').text(data.commentCount).appendTo($status);
        }
        $('<hr>').appendTo($a);
    },
    createListItem: function ($target, data) {
        const $a = $('<a></a>').addClass('list').appendTo($target);
        if (data.boardId && data.id) {
            $a.attr('href', '/' + data.boardId + '/view/' + data.id);
        }
        $('<time></time>').text(_gfn.formatRelativeDate(data.createdDate)).appendTo($a);
        const text = data.title ? data.title : data.text;
        $('<p></p>').html(text).appendTo($a);
        $('<hr>').appendTo($a);
    },
    createPhotoItem: function ($target, $data) {
        var $a = $('<a></a>').addClass('photo').appendTo($target);
        if ($data.attr('boardId') && $data.attr('id')) {
            $a.attr('href', '/' + $data.attr('boardId') + '/v/' + $data.attr('id'));
        }
        var attachUrl;
        if ($data.attr('attachId')) {
            var attachId = Number($data.attr('attachId'));
            if (attachId === -1) {
                attachUrl = '/images/attach.unauthorized.png';
            } else {
                var extension = $data.attr('attachFileName').split('.').pop().toUpperCase();
                var extensionsForImage = ['GIF', 'JPG', 'JPEG', 'PNG'];
                if (_.indexOf(extensionsForImage, extension) > -1) {
                    attachUrl = $data.attr('attachFileUrl');
                }
            }
        }
        if (!attachUrl) {
            attachUrl = '/images/attach.empty.png';
        }
        $a.css('background-image', 'url("' + attachUrl + '")');
        var text = $data.attr('title') ? $data.attr('title') : $data.attr('text');
        $('<p></p>').html(text).appendTo($a);
        $('<time></time>').text(_gfn.formatRelativeDate($data.attr('createdAt'))).appendTo($a);
    }
});

$(document).ready(function () {
    var $container = $('#container');
    var $rightside;
    var _fn = {
        initiate: function () {
            $rightside = $container.find('div.rightside');
            if ($container.find('aside').is(':has(form.search)')) {

                var $asideSearchForm = $container.find('aside > form.search');
                $('#searchArticle').on('click', function () {
                    $asideSearchForm.addClass('visible');
                    $asideSearchForm.find('input[name="keyword"]').focus();
                });
                $asideSearchForm.find('input[name="keyword"]').on('blur', function () {
                    $asideSearchForm.removeClass('visible');
                });
                $asideSearchForm.on('submit', function () {
                    _fn.submitSearch(this);
                    return false;
                });
                var $searchForm = $('<form></form>').addClass('search').appendTo($rightside);
                $('<input>').attr({
                    type: 'text',
                    name: 'keyword',
                    placeholder: '전체 게시판의 글을 검색하세요!'
                }).addClass('text').appendTo($searchForm);
                $searchForm.on('submit', function () {
                    _fn.submitSearch(this);
                    return false;
                });
            }
            if ($rightside.is(':visible')) {
                _fn.loadSide();
            }
            $(window).resize(function () {
                if ($rightside.is(':visible') && $rightside.is(':empty')) {
                    _fn.loadSide();
                }
            });
        },
        submitSearch: function (that) {
            var keyword = $(that).find('input[name="keyword"]').val().trim();
            if (keyword.length < 2) {
                alert('검색어를 두 글자 이상 입력하세요!');
                return false;
            }
            location.href = '/search/all/' + keyword;
        },
        loadSide: function () {
            _fn.ajaxSide(function (data) {
                if (!data) {
                    return false;
                }
                _fn.createSide(data);
            });
        },
        ajaxSide: function (callback) {
            const condition = {
                schoolId: $('#communitySchoolId').val()
            };
            $.ajax({
                url: '/find/community/webside',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                type: 'POST',
                data: JSON.stringify(condition),
                success: function (data) {
                    callback(data);
                }
            });
        },
        createSide: function (data) {
            if (data.popArticle.length) {
                _fn.createPopArticle(data.popArticle);
            }
            if (data.hotArticle.length) {
                _fn.createHotArticle(data.hotArticle);
            }
            _fn.createBestArticle();
            if (data.news.length) {
                _fn.createNews(data.news);
            }
            if (data.lecture.length) {
                _fn.createLecture(data.lecture);
            }
        },
        createPopArticle: function (data) {
            const $card = $('<div></div>').addClass('card');
            const $board = $('<div></div>').addClass('board').appendTo($card);
            const $h3 = $('<h3></h3>').appendTo($board);
            $('<a></a>').text('실시간 인기 글').appendTo($h3);
            for (let i = 0; i < data.length; i++) {
                _gfn.createArticleItem($board, data[i]);
            }
            $card.appendTo($rightside);
        },
        createHotArticle: function (data) {
            const $card = $('<div></div>').addClass('card');
            const $board = $('<div></div>').addClass('board').appendTo($card);
            const $h3 = $('<h3></h3>').appendTo($board);
            const $h3a = $('<a></a>').attr('href', '/hotarticle').text('HOT 게시물').appendTo($h3);
            $('<span></span>').text('더 보기').appendTo($h3a);
            for (let i = 0; i < data.length; i++) {
                _gfn.createListItem($board, data[i]);
            }
            $card.appendTo($rightside);
        },
        createBestArticle: function () {
            var $card = $('<div></div>').addClass('card');
            var $board = $('<div></div>').addClass('board').appendTo($card);
            var $h3 = $('<h3></h3>').appendTo($board);
            var $h3a = $('<a></a>').attr('href', '/bestarticle').text('BEST 게시판').appendTo($h3);
            $('<span></span>').text('더 보기').appendTo($h3a);
            $card.appendTo($rightside);
        },
        createNews: function ($data) {
            var $card = $('<div></div>').addClass('card');
            var $board = $('<div></div>').addClass('board').appendTo($card);
            var $h3 = $('<h3></h3>').appendTo($board);
            $('<a></a>').text('학교 소식').appendTo($h3);
            var $articlesData = $data.find('article');
            $articlesData.each(function () {
                _gfn.createArticleItem($board, $(this));
            });
            $card.appendTo($rightside);
        },
        createLecture: function ($data) {
            var $card = $('<div></div>').addClass('card');
            var $board = $('<div></div>').addClass('board').appendTo($card);
            var $h3 = $('<h3></h3>').appendTo($board);
            var $h3a = $('<a></a>').attr('href', '/lecture').text('최근 강의평').appendTo($h3);
            $('<span></span>').text('더 보기').appendTo($h3a);
            var $articlesData = $data.find('article');
            $articlesData.each(function () {
                _gfn.createArticleItem($board, $(this));
            });
            $card.appendTo($rightside);
        }
    };
    _fn.initiate();
});