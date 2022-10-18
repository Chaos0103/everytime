$().ready(function () {
    var $container = $('#container');
    var $title, $containerTitle, $articles;
    var _component = {
        Gallery: (function () {
            var _props = {
                data: []
            };
            var _refs = {
                isMounted: false
            };
            function _getHls(callback) {
                if (window.Hls === undefined) {
                    var script = document.createElement('script');
                    script.src = '/js/extensions.hls.light.min.js';
                    script.addEventListener('load', function() {
                        callback(null, window.Hls);
                    });
                    document.head.appendChild(script);
                } else {
                    callback(null, window.Hls);
                }
            }
            function _handleHls(url) {
                var video = document.createElement('video');
                var errorHandler = function () {
                    if ([MediaError.MEDIA_ERR_DECODE, MediaError.MEDIA_ERR_SRC_NOT_SUPPORTED].indexOf(video.error.code) !== -1) {
                        _getHls(function (err, Hls) {
                            var hls = new Hls({
                                xhrSetup: function (xhr) {
                                    xhr.withCredentials = true;
                                }
                            });
                            hls.loadSource(url);
                            hls.attachMedia(video);
                        });
                    }
                };
                video.addEventListener('error', errorHandler);
                video.autoplay = true;
                video.controls = true;
                video.crossOrigin = 'use-credentials';
                video.muted = true;
                video.src = url;
                return video;
            }
            function _handleImage(src, extension) {
                var $image = $('<div></div>');
                $('<img>').attr('src', src).appendTo($image);
                if (extension === 'mp4') {
                    $image.addClass('uploading')
                    $('<p></p>').text('동영상이 업로드되고 있어요').appendTo($image);
                }
                return $image;
            }
            return {
                props: _props,
                destroy: function () {
                    _refs.isMounted = false;
                    $('#gallery').remove();
                },
                isMounted: function () {
                    return _refs.isMounted;
                },
                mount: function (attachIndex) {
                    var row = _props.data[attachIndex];
                    if (!row) {
                        return;
                    }
                    _refs.isMounted = true;
                    var $gallery = $('<div></div>').attr('id', 'gallery');
                    $('<div></div>')
                        .addClass('backdrop')
                        .on('click', function () {
                            history.back();
                        })
                        .appendTo($gallery);
                    var inner;
                    if (row.hlsUrl) {
                        inner = _handleHls(row.hlsUrl);
                    } else {
                        inner = _handleImage(row.imageUrl || row.fileurl, row.extension);
                    }
                    $(inner)
                        .addClass('content')
                        .appendTo($gallery);
                    $gallery.appendTo($container);
                }
            };
        })()
    };
    var _set = {
        limitNum: 20,
        startNum: 0,
        isUser: false,
        boardId: undefined,
        boardPage: 1,
        bestarticleSeason: undefined,
        categoryId: undefined,
        categories: [],
        searchType: 0,
        keyword: '',
        moiminfo: true,
        type: 1,
        layout: 11,
        privAnonym: 0,
        privCommentAnonym: 0,
        isSearchable: 0,
        isWritable: 0,
        isCommentable: 0,
        isManageable: 0,
        isSecret: 0,
        isCommercial: 0,
        authToWrite: 0,
        authToComment: 0,
        placeholder: '글 내용을 입력하세요.',
        isNotSelectedHotArticle: -1,
        hashtags: [],
        attaches: [],
        removeAttachIds: [],
        attachDragHoverCount: 0,
        attachUploadingStatus: []
    };
    var _fn = {
        initiate: function () {
            if (!$container.is(':has(#boardId)')) {
                location.href = '/';
                return false;
            }
            $title = $container.find('aside > div.title');
            $containerTitle = $container.find('div.wrap.title');
            $articles = $container.find('div.articles');
            _set.isUser = ($container.find('#isUser').val() === '1') ? true : false;
            _set.boardId = $container.find('#boardId').val();

            _fn.initiateContent();
            $(window).on('load', function () { // Fix popstate issue in Safari
                setTimeout(function () {
                    $(window).on('popstate', function (event) {
                        var params = _fn.parseParams(window.location.pathname);
                        if (params.v) {
                            var state = event.originalEvent.state;
                            if (_component.Gallery.isMounted()) {
                                _component.Gallery.destroy();
                                return;
                            } else if (state && state['gallery-attach-index'] !== undefined) {
                                _component.Gallery.mount(state['gallery-attach-index']);
                                return;
                            }
                        }
                        _fn.initiateContent();
                    });
                }, 0);
            });
            $containerTitle.on('click', '#manageMoim', function () {
                _fn.manageMoim();
            });
            $container.on('click', '#writeArticleButton', function () {
                _fn.showWriteArticleForm();
            });
            $container.on('change', '#searchArticleForm > select[name="search_type"]', function () {
                var $form = $container.find('#searchArticleForm');
                var $keyword = $form.find('input[name="keyword"]');
                if ($(this).val() === '3') {
                    $keyword.attr('placeholder', '#에브리타임');
                } else {
                    $keyword.attr('placeholder', '검색어를 입력하세요.');
                }
                $keyword.val('');
            });
            $container.on('submit', '#searchArticleForm', function () {
                _fn.searchArticle();
                return false;
            });
            $container.on('click', '#goListButton', function () {
                if (_set.boardPage > 1) {
                    history.go(-1);
                } else {
                    var url = _fn.encodeUrl({ page: 1 });
                    _fn.goRedirectContent(url);
                }
            });
            $articles.on('click', 'a[href]', function (event) {
                _fn.goLinkContent(this, event);
            });
            $articles.on('click', '> article > a.article > ul.status > li.update', function () {
                var $article = $(this).parents('article');
                _fn.showWriteArticleForm($article);
                return false;
            });
            $articles.on('click', '> article > a.article > ul.status > li.del', function () {
                var $article = $(this).parents('article');
                if (confirm('이 글을 삭제하시겠습니까?')) {
                    _fn.removeArticle($article);
                }
                return false;
            });
            $articles.on('click', '> article > a.article > ul.status > li.abuse', function () {
                var $article = $(this).parents('article');
                _fn.showAbuseForm($article, 'article');
            });
            $articles.on('click', '> article > a.article > ul.status > li.removescrap', function () {
                var $article = $(this).parents('article');
                _fn.removeScrap($article);
                return false;
            });
            $articles.on('click', '> article > a.article > ul.status > li.setnotice', function () {
                var $article = $(this).parents('article');
                _fn.setNoticeArticle($article);
                return false;
            });
            $articles.on('click', '> article > a.article > ul.status > li.managedel', function () {
                var $article = $(this).parents('article');
                if (confirm('[삭제]는 관리하시는 게시판의 주제 및 규칙에 맞지 않는 게시물을 삭제하기 위한 기능입니다.\n\n확인을 누를 경우 게시물이 즉시 삭제됩니다.\n\n욕설, 음란물 등 에브리타임 커뮤니티 이용규칙에 어긋나는 게시물은 [삭제 및 이용 제한]을 해주시기 바랍니다.')) {
                    _fn.removeArticle($article);
                }
                return false;
            });
            $articles.on('click', '> article > a.article > ul.status > li.manageabuse', function () {
                var $article = $(this).parents('article');
                if (confirm('[삭제 및 이용 제한]은 욕설, 음란물 등 에브리타임 커뮤니티 이용규칙에 어긋나는 게시물을 삭제하고, 이용 제한을 하기 위한 기능입니다.\n\n확인을 누를 경우 게시물이 즉시 삭제되며, 작성자는 일정 기한 동안 이 게시판을 이용할 수 없습니다.\n\n단순히 주제 및 규칙에 맞지 않는 게시물의 경우 [삭제]를 해주시기 바랍니다.')) {
                    _fn.abuseArticle($article, 0);
                }
                return false;
            });
            $articles.on('click', '> article > a.article > div.buttons > span.posvote', function () {
                var $article = $(this).parents('article');
                _fn.voteArticle($article);
            });
            $articles.on('click', '> article > a.article > div.buttons > span.scrap', function () {
                var $article = $(this).parents('article');
                _fn.scrapArticle($article);
            });
            $articles.on('submit', '> form.write', function () {
                _fn.writeArticle();
                return false;
            });
            $articles.on('drag dragstart dragend dragover dragenter dragleave drop', '> form.write', function (event) {
                event.preventDefault();
                event.stopPropagation();
            }).on('dragenter', '> form.write', function (event) {
                _fn.dragstartOnWriteArticleForm(event);
            }).on('dragleave drop', '> form.write', function (event) {
                _fn.dragendOnWriteArticleForm(event);
            }).on('drop', '> form.write', function (event) {
                _fn.dropOnWriteArticleForm(event);
            });
            $articles.on('click', '> form.write > ul.hashtags > li', function () {
                _fn.addHashtagOnWriteArticleForm($(this).text());
            });
            $articles.on('change', '> form.write > input[name="file"]', function () {
                _fn.changeAttachOnWriteArticleForm(this.files);
            });
            $articles.on('click', '> form.write > ol.thumbnails > li.new', function () {
                _fn.addAttachOnWriteArticleForm();
            });
            $articles.on('click', '> form.write > ol.thumbnails > li.thumbnail.attached', function () {
                _fn.showAttachThumbnailForm($(this));
            });
            $articles.on('click', '> form.write > ul.option > li.anonym', function () {
                if ($(this).hasClass('active')) {
                    $(this).removeClass('active');
                } else {
                    $(this).addClass('active');
                }
            });
            $articles.on('click', '> form.write > ul.option > li.question', function () {
                var $question = $articles.find('form.write p.question');
                if ($(this).hasClass('active')) {
                    $question.hide();
                    $(this).removeClass('active');
                } else {
                    $question.show();
                    $(this).addClass('active');
                }
            });
            $articles.on('click', '> form.write > ul.option > li.hashtag', function () {
                _fn.addHashtagOnWriteArticleForm();
            });
            $articles.on('click', '> form.write > ul.option > li.attach', function () {
                _fn.addAttachOnWriteArticleForm();
            });
            $articles.on('click', '> form.write > ul.option > li.submit', function () {
                $articles.find('form.write').submit();
            });
            $articles.on('submit', '> article > div.comments > form.writecomment', function () {
                _fn.writeComment($(this));
                return false;
            });
            $articles.on('focus', '> article > div.comments > form.writecomment > input[name="text"]', function () {
                if (_set.authToComment) {
                    if (confirm('학교인증 회원만 댓글을 작성할 수 있습니다. 학교인증을 하시겠습니까?')) {
                        location.href = '/auth';
                    }
                    $(this).blur();
                }
            });
            $articles.on('click', '> article > div.comments > form.writecomment > ul.option > li.anonym', function () {
                var $this = $(this);
                if ($this.hasClass('active')) {
                    $this.removeClass('active');
                } else {
                    $this.addClass('active');
                }
            });
            $articles.on('click', '> article > div.comments > form.writecomment > ul.option > li.submit', function () {
                $(this).parents('form.writecomment').submit();
            });
            $articles.on('click', '> article > div.comments > article > ul.status > li.childcomment', function () {
                var $comment = $(this).parent().parent();
                _fn.createChildCommentForm($comment);
            });
            $articles.on('click', '> article > div.comments > article > ul.status > li.commentvote', function () {
                var $comment = $(this).parent().parent();
                _fn.voteComment($comment);
            });
            $articles.on('click', '> article > div.comments > article > ul.status > li.del', function () {
                var $comment = $(this).parent().parent();
                if (confirm('이 댓글을 삭제하시겠습니까?')) {
                    _fn.removeComment($comment);
                }
            });
            $articles.on('click', '> article > div.comments > article > ul.status > li.abuse', function () {
                var $comment = $(this).parent().parent();
                _fn.showAbuseForm($comment, 'comment');
            });
            $articles.on('click', '> article > div.comments > article > ul.status > li.managedel', function () {
                var $comment = $(this).parent().parent();
                if (confirm('[삭제]는 관리하시는 게시판의 주제 및 규칙에 맞지 않는 게시물을 삭제하기 위한 기능입니다.\n\n확인을 누를 경우 게시물이 즉시 삭제됩니다.\n\n욕설, 음란물 등 에브리타임 커뮤니티 이용규칙에 어긋나는 게시물은 [삭제 및 이용 제한]을 해주시기 바랍니다.')) {
                    _fn.removeComment($comment);
                }
            });
            $articles.on('click', '> article > div.comments > article > ul.status > li.manageabuse', function () {
                var $comment = $(this).parent().parent();
                if (confirm('[삭제 및 이용 제한]은 욕설, 음란물 등 에브리타임 커뮤니티 이용규칙에 어긋나는 게시물을 삭제하고, 이용 제한을 하기 위한 기능입니다.\n\n확인을 누를 경우 게시물이 즉시 삭제되며, 작성자는 일정 기한 동안 이 게시판을 이용할 수 없습니다.\n\n단순히 주제 및 규칙에 맞지 않는 게시물의 경우 [삭제]를 해주시기 바랍니다.')) {
                    _fn.abuseComment($comment, 0);
                }
            });
        },
        initiateContent: function () {
            _set.categoryId = _set.categoryId || 0;
            var url = window.location.pathname;
            var params = _fn.parseParams(url);
            _fn.loadContent(params);
        },
        goLinkContent: function (that, event) {
            event.stopPropagation();
            if (typeof history.pushState === 'undefined') {
                return false;
            }
            var url = $(that).attr('href');
            if (url.charAt(0) !== '/') { // 외부 URL
                return false;
            } else if (_set.boardId !== url.split('/')[1]) { // 현재 게시판 외 페이지
                return false;
            }
            event.preventDefault();
            var params = _fn.parseParams(url);
            _fn.loadContent(params);
            history.pushState(null, null, url);
        },
        goRedirectContent: function (url) {
            if (typeof history.pushState === 'undefined') {
                location.href = url;
                return false;
            }
            var params = _fn.parseParams(url);
            _fn.loadContent(params);
            history.pushState(null, null, url);
        },
        loadContent: function (params) {
            if (params.view) {
                $container.find('div.seasons, div.categories').addClass('none');
                _fn.loadComments(params.view);
            } else {
                _set.boardPage = 1;
                _set.searchType = 0;
                _set.keyword = '';
                if (params.p) {
                    _set.boardPage = Number(params.p);
                }
                if (params.hashtag) {
                    _set.searchType = 3;
                    _set.keyword = params.hashtag;
                } else if (params.title) {
                    _set.searchType = 2;
                    _set.keyword = params.title;
                } else if (params.text) {
                    _set.searchType = 1;
                    _set.keyword = params.text;
                } else if (params.all) {
                    _set.searchType = 4;
                    _set.keyword = params.all;
                }
                $container.find('div.seasons, div.categories').removeClass('none');
                _fn.loadArticles();
            }
        },
        parseParams: function (url) {
            var params = {};
            var paths = url.split('/').slice(2);
            for (var i = 0; i < paths.length; i += 2) {
                var key = paths[i];
                var value = paths[i + 1];
                if (/^\d+$/.test(value)) {
                    value = Number(value);
                } else {
                    value = decodeURI(value);
                }
                params[key] = value;
            }
            return params;
        },
        encodeUrl: function (params) {
            var url;
            if (typeof params.boardId !== 'undefined') {
                url = '/' + params.boardId;
                if (typeof params.articleId !== 'undefined') {
                    url += '/view/' + params.articleId;
                } else {
                    if (typeof params.hashtag !== 'undefined') {
                        url += '/hashtag/' + params.hashtag;
                    } else if (typeof params.title !== 'undefined') {
                        url += '/title/' + params.title;
                    } else if (typeof params.text !== 'undefined') {
                        url += '/text/' + params.text;
                    } else if (typeof params.all !== 'undefined') {
                        url += '/all/' + params.all;
                    }
                    if (typeof params.page !== 'undefined') {
                        url += '/p/' + params.page;
                    }
                }
            } else {
                url = '/' + _set.boardId;
                if (typeof params.articleId !== 'undefined') {
                    url += '/view/' + params.articleId;
                } else if (typeof params.hashtag !== 'undefined' || typeof params.title !== 'undefined' || typeof params.text !== 'undefined' || typeof params.all !== 'undefined') {
                    if (typeof params.hashtag !== 'undefined') {
                        url += '/hashtag/' + params.hashtag;
                    } else if (typeof params.title !== 'undefined') {
                        url += '/title/' + params.title;
                    } else if (typeof params.text !== 'undefined') {
                        url += '/text/' + params.text;
                    } else if (typeof params.all !== 'undefined') {
                        url += '/all/' + params.all;
                    }
                    if (typeof params.page !== 'undefined') {
                        url += '/p/' + params.page;
                    }
                } else {
                    if (_set.searchType === 3) {
                        url += '/hashtag/' + _set.keyword;
                    } else if (_set.searchType === 2) {
                        url += '/title/' + _set.keyword;
                    } else if (_set.searchType === 1) {
                        url += '/text/' + _set.keyword;
                    } else if (_set.searchType === 4) {
                        url += '/all/' + _set.keyword;
                    }
                    if (typeof params.page !== 'undefined') {
                        url += '/p/' + params.page;
                    } else {
                        url += '/p/' + _set.boardPage;
                    }
                }
            }
            return url;
        },
        createDialog: function (message) {
            $articles.find('div.loading').remove();
            $('<article></article>').addClass('dialog').html(message).appendTo($articles);
        },
        createMoimInfo: function (data) {
            let moimData;
            let boardName;
            let boardInfo;
            if (data.moim) {
                moimData = data.moim;
                _set.type = Number(moimData.type);
                _set.layout = Number(moimData.layout);
                _set.privAnonym = Number(moimData.privAnonym);
                _set.privCommentAnonym = Number(moimData.privCommentAnonym);
                _set.info = moimData.info
                _set.isQuestionable = Number(moimData.isQuestionable);
                _set.isSearchable = Number(moimData.isSearchable);
                _set.isWritable = Number(moimData.isWritable);
                _set.isCommentable = Number(moimData.isCommentable);
                _set.isManageable = Number(moimData.isManageable);
                _set.isSecret = Number(moimData.isSecret);
                _set.isCommercial = Number(moimData.isCommercial);
                _set.authToWrite = Number(moimData.authToWrite);
                _set.authToComment = Number(moimData.authToComment);
                if (moimData.placeholder) {
                    _set.placeholder = moimData.placeholder.replace(/<br\/>/gi, '\n');
                }
                if (moimData.isNotSelectedHotArticle) {
                    _set.isNotSelectedHotArticle = Number(moimData.isNotSelectedHotArticle);
                }
                boardName = moimData.name;
                let boardInfo = moimData.info;
                $('#submenu').find('a').filter(function () {
                    return $(this).data('id') === Number(_set.boardId);
                }).addClass('active');
            } else if (_set.boardId === 'search') {
                boardName = '\'' + _.escape(_set.keyword) + '\' 검색 결과';
            } else if (_set.boardId === 'myarticle') {
                boardName = '내가 쓴 글';
            } else if (_set.boardId === 'mycommentarticle') {
                boardName = '댓글 단 글';
            } else if (_set.boardId === 'myscrap') {
                boardName = '내 스크랩';
            } else if (_set.boardId === 'hotarticle') {
                boardName = 'HOT 게시판';
                boardInfo = '공감 10개를 받으면 HOT 게시물로 자동 선정됩니다.';
            } else if (_set.boardId === 'bestarticle') {
                boardName = 'BEST 게시판';
                boardInfo = '공감을 100개 이상 받은 게시물 랭킹입니다.';
            } else {
                return false;
            }
            $title.find('h1').remove();
            $containerTitle.empty();
            const $h1 = $('<h1></h1>').appendTo($title);
            const $titleA = $('<a></a>').html(boardName);
            if (_set.boardId !== 'search') {
                $titleA.attr('href', '/' + _set.boardId);
            }
            $titleA.appendTo($h1);
            // if (_set.isSearchable && $(data).find('response').is(':has(hashtags > recommendation)')) {
            //     var $hashtagsData = $(data).find('hashtags > recommendation');
            //     $hashtagsData.find('item').each(function () {
            //         _set.hashtags.push($(this).text());
            //     });
            // }
            if (moimData && moimData.isPrimary === '0') {
                const $buttons = $('<ol></ol>').addClass('buttons');
                const $li = $('<li></li>').appendTo($buttons);
                if (_set.isUser) {
                    if (_set.isManageable) {
                        $('<a></a>').attr('id', 'manageMoim').text('관리하기').appendTo($li);
                    }
                }
                if ($li.is(':has(a)')) {
                    $buttons.appendTo($containerTitle);
                }
            }
            const $containerH1 = $('<h1></h1>').appendTo($containerTitle);
            const $containerTitleA = $('<a></a>').html(boardName);
            if (_set.boardId !== 'search') {
                $containerTitleA.attr('href', '/' + _set.boardId);
            }
            $containerTitleA.appendTo($containerH1);
            if (boardInfo) {
                $('<p></p>').html(boardInfo).appendTo($containerTitle);
            }
            $('<hr>').appendTo($containerTitle);
            if (_set.isSecret) {
                $('body').addClass('selectDisabled');
                $(document).on('contextmenu', function (e) {
                    if (!e.target || (e.target.tagName.toUpperCase() !== 'TEXTAREA' && e.target.tagName.toUpperCase() !== 'INPUT')) {
                        alert('이 게시판의 내용을 커뮤니티 외부로 유출하는 것은 금지되어 있습니다. 게시물을 복사·스크린샷·촬영하여 외부 사이트·대화방에 게시하거나 타인에게 공유하는 등 유출이 적발될 경우, 서비스 이용 제한 등의 조치가 취해집니다.');
                        return false;
                    }
                });
            }
        },
        createBestarticleSeasons: function () {

            var seasons = [];

            var minYear = 2018;
            var cal = new Date();
            var year = cal.getFullYear();
            var month = cal.getMonth() + 1;
            var half = month <= 6 ? 1 : 2;
            while (year !== minYear - 1) {
                seasons.push(year * 10 + half);
                if (half === 2) {
                    half--;
                } else {
                    half = 2;
                    year--;
                }
            }
            _set.bestarticleSeason = seasons[0];

            var $seasons = $('<div></div>')
                .addClass('wrap seasons')
                .insertAfter($containerTitle);

            _.each(seasons, function (season, idx) {
                $('<div></div>')
                    .addClass('season')
                    .data({ value: season })
                    .html('<span>' + String(season).slice(-3, -1) + '년 ' + (String(season).slice(-1) === '2' ? '하반기' : '상반기') + '</span>')
                    .on('click', function () {
                        _fn.setSeason(this);
                    })
                    .appendTo($seasons);
            });

        },
        createCategories: function ($categoriesXml) {

            var $categories = $('<div></div>')
                .addClass('wrap categories')
                .insertAfter($containerTitle);

            $('<div></div>').addClass('category selected').data({
                id: '0',
                name: ''
            }).html('<span>전체</span>').on('click', function () {
                _fn.setCategory(this);
            }).appendTo($categories);

            $categoriesXml.find('category').each(function () {
                var data = {
                    id: $(this).attr('id'),
                    name: $(this).attr('name')
                };
                _set.categories.push(data);
                $('<div></div>')
                    .addClass('category')
                    .data(data)
                    .html('<span>' + data.name + '</span>')
                    .on('click', function () {
                        _fn.setCategory(this);
                    })
                    .appendTo($categories);
            });

        },
        loadArticles: function () {
            $(window).scrollTop(0);
            $articles.empty();
            $('<div></div>').text('불러오는 중입니다...').addClass('loading').appendTo($articles);
            _set.startNum = _set.limitNum * (_set.boardPage - 1);
            if (_set.moiminfo && _set.boardId === 'bestarticle') {
                _fn.createBestarticleSeasons();
            }
            _fn.ajaxArticles(function (data) {
                if (_set.moiminfo) {
                    var $responseXml = $(data).find('response');
                    if ($responseXml.is(':has(categories)')) {
                        _fn.createCategories($responseXml.find('categories'));
                    }
                    _set.moiminfo = false;
                    _fn.createMoimInfo(data);
                }
                _fn.createArticles(data, true);
                $container.find('div.seasons div.season').each(function (idx, elem) {
                    $(elem)[$(elem).data('value') === _set.bestarticleSeason ? 'addClass' : 'removeClass']('selected');
                });
                $container.find('div.categories div.category').each(function (idx, elem) {
                    $(elem)[Number($(elem).data('id')) === _set.categoryId ? 'addClass' : 'removeClass']('selected');
                });
            });
        },
        ajaxArticles: function (callback) {
            var conditions = {
                id: _set.boardId,
                limit_num: _set.limitNum,
                start_num: _set.startNum
            };
            if (_set.moiminfo) {
                conditions.moiminfo = 'true';
            }
            if (_set.searchType > 0 && _set.keyword !== '') {
                conditions.search_type = _set.searchType;
                conditions.keyword = _set.keyword;
            }
            if (_set.bestarticleSeason !== undefined) {
                conditions.season = _set.bestarticleSeason;
            }
            if (_set.categoryId > 0) {
                conditions.category_id = _set.categoryId;
            }
            $.ajax({
                url: '/find/board/article/list',
                type: 'POST',
                data: conditions,
                success: function (data) {
                    var responseCode;
                    if (!$(data).find('response').children().length) {
                        responseCode = $(data).find('response').text();
                    }
                    if (responseCode === '0') {
                        if (_set.isUser) {
                            _fn.createDialog('게시판이 존재하지 않습니다.');
                        } else {
                            location.href = '/login?redirect=' + location.pathname;
                        }
                    } else if (responseCode === '-100') {
                        if (confirm('학교인증 회원만 접근할 수 있습니다. 학교인증을 하시겠습니까?')) {
                            location.href = '/auth';
                        } else {
                            history.go(-1);
                        }
                    } else if (responseCode === '-300' || responseCode === '-400') {
                        _fn.createDialog('접근 권한이 없습니다.');
                    } else {
                        callback(data);
                    }
                }
            });
        },
        createArticles: function (data, isListItem) {
            $articles.empty();
            if (_set.isWritable || _set.authToWrite) {
                $('<a></a>').attr('id', 'writeArticleButton').text('새 글을 작성해주세요!').appendTo($articles);
            }
            if (_set.hashtags.length > 0) {
                var $hashtags = $('<ul></ul>').addClass('hashtags').appendTo($articles);
                for (var i in _set.hashtags) {
                    var hashtag = _set.hashtags[i];
                    var $li = $('<li></li>').appendTo($hashtags);
                    var hashtagUrl = _fn.encodeUrl({ hashtag: hashtag });
                    $('<a></a>').attr('href', hashtagUrl).text('#' + hashtag).appendTo($li);
                }
                $('<div></div>').addClass('clearBothOnly').appendTo($hashtags);
            }
            if ($(data).find('response').is(':has(notice_article)')) {
                var $noticeData = $(data).find('notice_article');
                var $notice = $('<div></div>').addClass('notice').appendTo($articles);
                var articleUrl = _fn.encodeUrl({ articleId: $noticeData.attr('id') });
                var $a = $('<a></a>').attr('href', articleUrl).html($noticeData.attr('text')).appendTo($notice);
            }

            const articles = data.articles;
            for(let i=0;i<articles.length;i++) {
                const article = articles[i];
                const isMine = Number(article.isMine);
                //isNotice
                const isQuestion = Number(article.isQuestion);
                const text = _fn.parseArticleText(article.text).replace(/<br\/>/gi, '\n');
                const $article = $('<article></article>').data({
                    id: article.id,
                    'isMine': article.isMine
                });
                if (article.boardId) {
                    $article.data('boardId', article.boardId);
                }
                const $a = $('<a></a>').addClass('article').appendTo($article);
                const $picture = $('<img>').attr('src', article.userPicture).addClass('picture');
                const $nickname = $('<h3></h3>').html(article.userNickname).addClass(article.userType);
                const $title = $('<h2></h2>').html(article.title);
                const $text = $('<p></p>').html(text);
                const $time = $('<time></time>').text(_gfn.formatRelativeDate(article.createdDate));
                const $category = $('<span></span>').addClass('category').text(article.category);
                const $status = $('<ul></ul>').addClass('status');
                if (_set.boardId === 'myscrap') {
                    $('<li></li>').addClass('removescrap').text('스크랩 취소').appendTo($status);
                }
                const attaches = article.attaches;
                if (attaches.length > 0) {
                    $('<li></li>').addClass('attach').text(attaches.length).appendTo($status);
                }
                const $vote = $('<li></li>').attr('title', '공감').addClass('vote').text(article.posvote).appendTo($status);
                const $comment = $('<li></li>').attr('title', '댓글').addClass('comment').text(article.comment).appendTo($status);
                if (isListItem) {
                    let articleUrl;
                    if (_.contains(['search', 'myarticle', 'mycommentarticle', 'myscrap', 'hotarticle', 'bestarticle'], _set.boardId)) {
                        articleUrl = _fn.encodeUrl({ boardId: article.boardId, articleId: article.id });
                    } else {
                        articleUrl = _fn.encodeUrl({ articleId: article.id });
                    }
                    $a.attr('href', articleUrl);
                    if (_set.layout === 11 || _set.layout === 12) {
                        $picture.addClass('medium').appendTo($a);
                        $nickname.addClass('medium').appendTo($a);
                        $category.addClass('medium');
                        $time.addClass('medium').appendTo($a);
                        $('<hr>').appendTo($a);
                        if (article.title) {
                            $title.addClass('medium bold').appendTo($a);
                        }
                        $text.addClass('medium').appendTo($a);
                        if (article.text.split('<br />').length > 5) {
                            $('<span></span>').text('... 더 보기').addClass('more').appendTo($a);
                        }
                        if (article.boardId) {
                            var boardUrl = _fn.encodeUrl({ boardId: article.boardId });
                            $('<a></a>').attr('href', boardUrl).html(article.boardName).addClass('boardname').appendTo($a);
                        }
                    } else if (_set.layout === 21 || _set.layout === 22) {
                        if (_set.layout === 21 && attaches.length > 0) {
                            const attach = attaches[0];
                            let filepath;
                            if (Number(attach.id) === -1) {
                                filepath = '/images/attach.unauthorized.png';
                            } else {
                                filepath = '/file/' + attach.fileName;
                                console.log(filepath);
                            }
                            // $('<div></div>').addClass('attachthumbnail').css('background-image', 'url("' + filepath + '")').appendTo($a);
                            $('<img/>').addClass('attachthumbnail').attr('src', filepath).appendTo($a);
                        }
                        $title.addClass('medium').appendTo($a);
                        if (_set.layout === 21) {
                            $text.addClass('small').appendTo($a);
                        }
                        $category.addClass('small');
                        $time.addClass('small').appendTo($a);
                        $nickname.addClass('small').appendTo($a);
                    }
                    $status.appendTo($a);
                    $('<hr>').appendTo($a);
                    if (article.category) {
                        $category.insertBefore($time);
                    }
                } else {
                    $article.data('article', article);
                    $picture.addClass('large').appendTo($a);
                    const $profile = $('<div></div>').addClass('profile').appendTo($a);
                    $nickname.addClass('large').appendTo($profile);
                    $time.addClass('large').appendTo($profile);
                    const $status2 = $('<ul></ul>').addClass('status').appendTo($a);
                    if (_set.isUser) {
                        if (_set.isManageable && !isNotice) {
                            $('<li></li>').text('공지로 설정').addClass('setnotice').appendTo($status2);
                        }
                        if (isMine) {
                            if (_set.type === 2) {
                                $('<li></li>').text('수정').addClass('update').appendTo($status2);
                            }
                            $('<li></li>').text('삭제').addClass('del').appendTo($status2);
                        } else {
                            $('<li></li>').text('쪽지').addClass('messagesend').attr({'data-modal': 'messageSend', 'data-article-id': article.id, 'data-is-anonym': Number(article.userId === '0')}).appendTo($status2);
                            if (_set.isManageable) {
                                $('<li></li>').text('삭제').addClass('managedel').appendTo($status2);
                                $('<li></li>').text('삭제 및 이용 제한').addClass('manageabuse').appendTo($status2);
                            } else {
                                $('<li></li>').text('신고').addClass('abuse').appendTo($status2);
                            }
                        }
                    }
                    $('<hr>').appendTo($a);
                    if (_set.type === 2) {
                        $title.addClass('large').appendTo($a);
                    }
                    $text.addClass('large').appendTo($a);
                    if (isQuestion === 1) {
                        $('<div></div>')
                            .addClass('question')
                            .html('이 글은 댓글이 달린 이후에는 수정 및 삭제가 불가능하므로, <b>작성하신 댓글이 삭제될 우려가 없어요.</b><br>보다 많은 학우들에게 도움이 될 수 있도록 정성이 담긴 답변을 부탁드려요.')
                            .appendTo($a);
                    }
                    //$attachesData
                    const $scrap = $('<li></li>').attr('title', '스크랩').addClass('scrap').text(article.scrapCount).appendTo($status);
                    $status.addClass('left').appendTo($a);
                    $('<hr>').appendTo($a);
                    const $buttons = $('<div></div>').addClass('buttons');
                    $('<span></span>').addClass('posvote').text('공감').appendTo($buttons);
                    $('<span></span>').addClass('scrap').text('스크랩').appendTo($buttons);
                    $buttons.appendTo($a);
                }
                $('<input/>').attr({
                    type: 'hidden',
                    name: article.id + '_comment_anonym',
                    value: article.commentAnonym
                }).appendTo($a);
                const $comments = $('<div></div>').addClass('comments').appendTo($article);
                $article.appendTo($articles);
            }
            if (!$articles.is(':has(article)')) {
                var message;
                if (_set.boardId === 'myarticle') {
                    message = '아직 글을 한번도 쓰지 않으셨군요.<br>원하는 게시판에 들어가서 설레는 첫 글을 작성해 보세요!';
                } else if (_set.boardId === 'mycommentarticle') {
                    message = '아직 댓글을 한번도 쓰지 않으셨군요.<br>원하는 글에 하고싶은 말을 댓글로 남겨보세요!';
                } else if (_set.boardId === 'myscrap') {
                    message = '아직 스크랩한 글이 없습니다.';
                } else if (_set.boardId === 'hotarticle') {
                    message = '아직 HOT 게시물이 없습니다.';
                } else if (_set.boardPage > 1) {
                    message = '더 이상 글이 없습니다.';
                } else if (_set.searchType > 0 && _set.keyword !== '') {
                    message = '검색 결과가 없습니다.';
                } else {
                    message = '아직 글이 하나도 없군요.<br>첫 글의 주인공이 되어보세요!';
                }
                _fn.createDialog(message);
            }
            $('<div></div>').addClass('clearBothOnly').appendTo($articles);
            var $pagination = $('<div></div>').addClass('pagination').appendTo($articles);
            if (_set.boardPage > 2) {
                var firstPageUrl = _fn.encodeUrl({ page: 1 });
                $('<a></a>').attr('href', firstPageUrl).text('처음').addClass('first').appendTo($pagination);
            }
            if (_set.boardPage > 1) {
                var prevPageUrl = _fn.encodeUrl({ page: (_set.boardPage - 1) });
                $('<a></a>').attr('href', prevPageUrl).text('이전').addClass('prev').appendTo($pagination);
            }
            if (_set.boardPage === 1 && _set.isSearchable && !_set.categoryId) {
                var $searchForm = $('<form></form>').attr('id', 'searchArticleForm').addClass('search').appendTo($pagination);
                var $searchType = $('<select></select>').attr({
                    name: 'search_type'
                }).appendTo($searchForm);
                $('<option></option>').val('4').text('전체').appendTo($searchType);
                $('<option></option>').val('3').text('해시태그').appendTo($searchType);
                if (_set.type === 2) {
                    $('<option></option>').val('2').text('글 제목').appendTo($searchType);
                }
                $('<option></option>').val('1').text('글 내용').appendTo($searchType);
                var $keyword = $('<input>').attr({
                    name: 'keyword',
                    placeholder: '검색어를 입력하세요.'
                }).addClass('text').appendTo($searchForm);
                if (_set.searchType > 0) {
                    var defaultKeyword = _set.keyword;
                    if (_set.searchType === 3) {
                        defaultKeyword = '#' + defaultKeyword;
                    }
                    $searchType.val(_set.searchType);
                    $keyword.val(defaultKeyword);
                }
            }
            if (!$articles.is(':has(article.dialog)')) {
                var nextPageUrl = _fn.encodeUrl({ page: (_set.boardPage + 1)});
                $('<a></a>').attr('href', nextPageUrl).text('다음').addClass('next').appendTo($pagination);
            }
        },
        parseArticleText: function (text) {
            if (!_set.isSearchable) {
                return text;
            }
            var searchUrl = _fn.encodeUrl({ hashtag: '' });
            var $temp = $('<div></div>').html(text.replace(/&lt/gi, '&amp;lt'));
            $temp.contents().filter(function () {
                return this.nodeType === 3;
            }).each(function () {
                $(this).replaceWith($(this).text().replace(/#([a-z0-9ㄱ-ㅎㅏ-ㅣ가-힣_]+)/gi, '<a href="' + searchUrl + '$1" class="hashtag">#$1</a>'));
            });
            return $temp.html();
        },
        loadComments: function (articleId) {
            $(window).scrollTop(0);
            $articles.empty();
            $('<div></div>').text('불러오는 중입니다...').addClass('loading').appendTo($articles);
            _fn.ajaxComments(articleId, function (data) {
                if (_set.moiminfo) {
                    _fn.createMoimInfo(data);
                }
                _fn.createComments(data);
            });
        },
        ajaxComments: function (articleId, callback) {
            var conditions = {
                id: articleId,
                limit_num: -1,
                articleInfo: 'true'
            };
            if (_set.moiminfo) {
                conditions.moiminfo = 'true';
            }
            $.ajax({
                url: '/find/board/comment/list',
                type: 'POST',
                data: conditions,
                success: function (data) {
                    var responseCode;
                    if (!$(data).find('response').children().length) {
                        responseCode = $(data).find('response').text();
                    }
                    if (responseCode === '0') {
                        location.href = '/login?redirect=' + location.pathname;
                    } else if (responseCode === '-1') {
                        if (_set.isUser) {
                            _fn.createDialog('글이 존재하지 않습니다.');
                        } else {
                            location.href = '/login?redirect=' + location.pathname;
                        }
                    } else if (responseCode === '-100') {
                        if (confirm('학교인증 회원만 볼 수 있습니다. 학교인증을 하시겠습니까?')) {
                            location.href = '/auth';
                        } else {
                            history.go(-1);
                        }
                    } else if (responseCode === '-200') {
                        alert('비밀번호 필요');
                    } else if (responseCode === '-300' || responseCode === '-400') {
                        _fn.createDialog('접근 권한이 없습니다.');
                    } else {
                        callback(data);
                    }
                }
            });
        },
        createComments: function (data) {
            $articles.empty();
            _fn.createArticles(data, false);
            _fn.hideWriteArticleButton();
            $articles.find('ul.hashtags').remove();
            const $article = $articles.find('> article').first();
            const $comments = $article.find('div.comments');
            const comments = data.comments;
            for (let i = 0; i < comments.length; i++) {
                const comment = comments[i];
                const $comment = $('<article></article>').data({
                    id: comment.id,
                    parentId: comment.parentId,
                    'is_mine': comment.isMine
                }).appendTo($comments);
                if (comment.parentId !== 0) {
                    $comment.addClass('child');
                } else {
                    $comment.addClass('parent');
                }
                $('<img/>').attr('src', comment.userPicture).addClass('picture medium').appendTo($comment);
                $('<h3></h3>').html(comment.userNickname).addClass('medium').addClass(comment.userType).appendTo($comment);
                const $status = $('<ul></ul>').addClass('status').appendTo($comment);
                if (_set.isUser && comment.id !== 0) {
                    if (comment.parentId === 0 && (_set.isCommentable === 1 || _set.authToComment === 1)) {
                        $('<li></li>').text('대댓글').addClass('childcomment').appendTo($status);
                    }
                    $('<li></li>').text('공감').addClass('commentvote').appendTo($status);
                    if (comment.isMine === 1) {
                        $('<li></li>').text('삭제').addClass('del').appendTo($status);
                    } else {
                        $('<li></li>').text('쪽지').addClass('messagesend').attr({'data-modal': 'messageSend', 'data-comment-id': comment.id, 'data-is-anonym': Number(comment.userId === 0)}).appendTo($status);
                        if (_set.isManageable) {
                            $('<li></li>').text('삭제').addClass('managedel').appendTo($status);
                            $('<li></li>').text('삭제 및 이용 제한').addClass('manageabuse').appendTo($status);
                        } else {
                            $('<li></li>').text('신고').addClass('abuse').appendTo($status);
                        }
                    }
                }
                $('<hr>').appendTo($comment);
                $('<p></p>').html(comment.text).addClass('large').appendTo($comment)
                    .after(
                        $('<time></time>').text(_gfn.formatRelativeDate(comment.createdDate)).addClass('medium').appendTo($comment),
                        $('<ul></ul>').addClass('status commentvotestatus').append(
                            $('<li></li>')
                                .addClass('vote commentvote')
                                .text(comment.posvote)
                                [comment.posvote === 0 ? 'hide' : 'show']()
                        )
                    );
            }
            if (_set.isCommentable || _set.authToComment) {
                const $writecomment = $('<form></form>').addClass('writecomment').appendTo($comments);
                $('<input>').attr({
                    type: 'text',
                    name: 'text',
                    maxlength: '300',
                    autocomplete: 'off',
                    placeholder: '댓글을 입력하세요.'
                }).addClass('text').appendTo($writecomment);
                const $option = $('<ul></ul>').addClass('option').appendTo($writecomment);
                if ($('input[name=' + $article.data('id') + '_comment_anonym]').val() === '0' && _set.privCommentAnonym !== 1) {
                    $('<li></li>').attr('title', '익명').addClass('anonym').appendTo($option);
                }
                $('<li></li>').attr('title', '완료').addClass('submit').appendTo($option);
                $('<div></div>').addClass('clearBothOnly').appendTo($writecomment);
            }
            $comments.show();
            const $pagination = $articles.find('> div.pagination');
            $pagination.empty();
            $('<a></a>').attr('id', 'goListButton').text('글 목록').addClass('list').appendTo($pagination);
        },
        manageMoim: function () {
            var $form = $container.find('#manageMoimForm');
            var $info = $form.find('input[name="info"]');
            var $isNotSelectedHotArticle = $form.find('input[name="is_not_selected_hot_article"]');
            $info.val(_set.info);
            if (_set.isNotSelectedHotArticle > -1) {
                $isNotSelectedHotArticle.parent('p').removeClass('hide');
                if (_set.isNotSelectedHotArticle === 1) {
                    $isNotSelectedHotArticle.prop('checked', true);
                }
            }
            $form.show();
            $form.on('submit', function () {
                var params = {
                    id: _set.boardId,
                    info: $info.val()
                };
                if (_set.isNotSelectedHotArticle > -1) {
                    params.is_not_selected_hot_article = $isNotSelectedHotArticle.is(':checked') ? '1' : '0';
                }
                $.ajax({
                    url: _apiServerUrl + '/update/board',
                    xhrFields: {withCredentials: true},
                    type: 'POST',
                    data: params,
                    success: function (data) {
                        var responseCode = $(data).find('response').text();
                        if (responseCode === '0') {
                            alert('수정할 수 없습니다.');
                        } else {
                            alert('변경된 설정을 저장하였습니다.');
                            location.reload();
                        }
                    }
                });
                return false;
            });
            $form.find('a.close').on('click', function () {
                $form.hide();
            });
            $form.find('.button[value="게시판 양도"]').on('click', function () {
                if (confirm('게시판을 다른 이용자에게 양도하시겠습니까?')) {
                    $form.hide();
                    _fn.transferMoim();
                }
            });
            $form.find('.button[value="게시판 삭제"]').on('click', function () {
                if (!confirm('게시판을 삭제하면 모든 글이 삭제되며 다시 복구할 수 없습니다.')) {
                    return false;
                }
                $.ajax({
                    url: _apiServerUrl + '/remove/board',
                    xhrFields: {withCredentials: true},
                    type: 'POST',
                    data: {
                        id: _set.boardId
                    },
                    success: function (data) {
                        var responseCode = $(data).find('response').text();
                        if (responseCode === '-1') {
                            alert('삭제할 수 없습니다.');
                        } else if (responseCode === '-2') {
                            alert('게시판 개설 혹은 마지막 게시물 게시 이후, 14일 동안 활동이 없는 게시판만 삭제할 수 있습니다.');
                        } else {
                            alert('게시판을 삭제하였습니다.');
                            location.href = '/';
                        }
                    }
                });
            });
        },
        transferMoim: function () {
            var $form = $container.find('#transferMoimForm');
            var $transfererPassword = $form.find('input[name="transferer_password"]');
            var $transfereeUserid = $form.find('input[name="transferee_userid"]');
            $form.show();
            $form.on('submit', function () {
                if (!$transfererPassword.val()) {
                    alert('양도인의 비밀번호를 입력하세요.');
                    $transfererPassword.focus();
                    return false;
                } else if (!$transfereeUserid.val()) {
                    alert('피양도인의 아이디를 입력하세요.');
                    $transfereeUserid.focus();
                    return false;
                }
                $.ajax({
                    url: _apiServerUrl + '/save/board/transferRequest',
                    xhrFields: {withCredentials: true},
                    type: 'POST',
                    data: {
                        board_id: _set.boardId,
                        transferer_password: $transfererPassword.val(),
                        transferee_userid: $transfereeUserid.val()
                    },
                    success: function (data) {
                        var responseCode = $(data).find('response').text();
                        if (responseCode === '0' || responseCode === '-1' || responseCode === '-2') {
                            alert('게시판을 양도할 수 없습니다.');
                        } else if (responseCode === '-3') {
                            alert('양도인(본인)의 비밀번호를 바르게 입력하세요.');
                        } else if (responseCode === '-4') {
                            alert('존재하지 않는 피양도인 아이디입니다.');
                        } else {
                            alert('게시판 양도를 요청하였습니다.\n요청 수락 후 게시판이 자동으로 양도됩니다.');
                            $form.hide();
                        }
                    }
                });
                return false;
            });
            $form.find('a.close').on('click', function () {
                $form.hide();
            });
        },
        setSeason: function (that) {
            var season = $(that).data('value');
            var boardUrl = _fn.encodeUrl({ boardId: _set.boardId });
            if (location.pathname !== boardUrl) {
                if (typeof history.pushState !== 'undefined') {
                    _set.boardPage = 1;
                    history.pushState(null, null, boardUrl);
                }
            }
            _set.searchType = 0;
            _set.keyword = '';
            _set.bestarticleSeason = season;
            _fn.loadArticles();
        },
        setCategory: function (that) {
            var categoryId = Number($(that).data('id'));
            var boardUrl = _fn.encodeUrl({ boardId: _set.boardId });
            if (location.pathname !== boardUrl) {
                if (typeof history.pushState !== 'undefined') {
                    _set.boardPage = 1;
                    history.pushState(null, null, boardUrl);
                }
            }
            _set.searchType = 0;
            _set.keyword = '';
            _set.categoryId = categoryId;
            _fn.loadArticles();
        },
        hideWriteArticleButton: function () {
            $('#writeArticleButton').hide();
        },
        showAbuseForm: function ($item, mode) {
            var $form = $container.find('#abuseForm');
            $form.show();
            $form.find('a.close').off('click').on('click', function () {
                $form.hide();
            });
            $form.find('ul > li > a').off('click').on('click', function () {
                var $this = $(this);
                var reason = Number($this.data('reason')) || 0;
                var message = '[' + $this.text() + ']\n';
                if (reason === 1) {
                    message += '게시물의 주제가 게시판의 성격에 크게 벗어나, 다른 이용자에게 불편을 끼칠 수 있는 게시물';
                } else if (reason === 2) {
                    message += '비아냥, 비속어 등 예의범절에 벗어나거나, 특정인이나 단체, 지역을 비방하는 등 논란 및 분란을 일으킬 수 있는 게시물';
                } else if (reason === 3) {
                    message += '청소년유해매체물, 외설, 음란물, 음담패설, 신체사진을 포함하거나, 불건전한 만남, 채팅, 대화, 통화를 위한 게시물';
                } else if (reason === 4) {
                    message += '타 서비스, 앱, 사이트 등 게시판 외부로 회원을 유도하거나 공동구매, 할인 쿠폰, 홍보성 이벤트 등 허가되지 않은 광고/홍보 게시물';
                } else if (reason === 5) {
                    message += '게시물 무단 유출, 타인의 개인정보 유출, 관리자 사칭 등 타인의 권리를 침해하거나 관련법에 위배되는 게시물';
                } else if (reason === 6) {
                    message += '중복글, 도배글, 낚시글, 내용 없는 게시물';
                } else if (reason === 7) {
                    message += '특정 정당이나 정치인에 대한 비난/비하/모욕 또는 지지/홍보/선거운동 및 선거 관련법에 위배되는 게시물';
                }
                message += '\n\n신고는 반대 의견을 나타내는 기능이 아닙니다. 신고 사유에 맞지 않는 신고를 했을 경우, 해당 신고는 처리되지 않습니다.';
                if (confirm(message)) {
                    if (mode === 'article') {
                        _fn.abuseArticle($item, reason);
                    } else if (mode === 'comment') {
                        _fn.abuseComment($item, reason);
                    }
                }
            });
        },
        showWriteArticleForm: function ($article) {
            if (_set.authToWrite) {
                if (confirm('학교인증 회원만 글을 작성할 수 있습니다. 학교인증을 하시겠습니까?')) {
                    location.href = '/auth';
                }
                return false;
            }

            _set.attaches = [];
            _set.removeAttachIds = [];
            _set.attachDragHoverCount = 0;
            _set.attachUploadingStatus = [];

            _fn.hideWriteArticleButton();
            var $form = $('<form></form>').addClass('write').prependTo($articles);

            if (_set.type === 2) {
                var $title = $('<input>').attr({
                    name: 'title',
                    autocomplete: 'off',
                    placeholder: '글 제목'
                }).addClass('title').appendTo($('<p></p>').appendTo($form));
            }
            var $text = $('<textarea></textarea>').attr({
                name: 'text',
                placeholder: _set.placeholder
            }).appendTo($('<p></p>').appendTo($form));
            if (_set.placeholder.length >= 50) {
                $text.addClass('smallplaceholder');
            }
            if (_set.type === 2) {
                $title.focus();
            } else {
                $text.focus();
            }
            if (_set.hashtags.length > 0) {
                var $hashtags = $('<ul></ul>').addClass('hashtags').appendTo($form);
                for (var i in _set.hashtags) {
                    var hashtag = _set.hashtags[i];
                    $('<li></li>').text('#' + hashtag).appendTo($hashtags);
                }
                $('<div></div>').addClass('clearBothOnly').appendTo($hashtags);
            }
            var $file = $('<input>').addClass('file').attr({type: 'file', name: 'file', multiple: true}).appendTo($form);
            var $thumbnails = $('<ol></ol>').addClass('thumbnails').appendTo($form);
            var $thumbnailsNewButton = $('<li></li>').addClass('new').appendTo($thumbnails);
            $('<div></div>').addClass('clearBothOnly').appendTo($form);
            var $question = $('<p></p>')
                .addClass('question')
                .appendTo($form);
            $('<div></div>')
                .html('질문 글을 작성하면 게시판 상단에 일정 기간 동안 노출되어, 더욱 빠르게 답변을 얻을 수 있게 됩니다.<br>또한, 다른 학우들이 정성껏 작성한 답변을 유지하기 위해, 댓글이 달린 이후에는 <b>글을 수정 및 삭제할 수 없습니다.</b>')
                .appendTo($question);
            var $option = $('<ul></ul>').addClass('option').appendTo($form);
            if (_set.isSearchable) {
                $('<li></li>').attr('title', '해시태그').addClass('hashtag').appendTo($option);
            }
            $('<li></li>').attr('title', '첨부').addClass('attach').appendTo($option);
            $('<li></li>').attr('title', '완료').addClass('submit').appendTo($option);
            if (_set.privAnonym !== 1) {
                $('<li></li>').attr('title', '익명').addClass('anonym').appendTo($option);
            }
            if (_set.isQuestionable === 1) {
                $('<li></li>').attr('title', '질문').addClass('question').appendTo($option);
            }
            $('<div></div>').addClass('clearBothOnly').appendTo($form);
            if ($article && $article.data('article')) {
                $article.hide();
                var $pagination = $articles.find('div.pagination');
                $pagination.find('a.list').hide();
                $('<a></a>').text('글 수정 취소').addClass('cancel').on('click', function () {
                    $pagination.find('a.list').show();
                    $article.show();
                    $(this).remove();
                    $articles.find('form.write').remove();
                }).appendTo($pagination);
                var $articleData = $article.data('article');
                $title.val($articleData.attr('raw_title'));
                $text.val($articleData.attr('raw_text'));
                $('<input>').attr({
                    type: 'hidden',
                    name: 'article_id'
                }).val($article.data('id')).appendTo($form);
                if ($articleData.find('attach').length > 0) {
                    $thumbnails.show();
                    $articleData.find('attach').each(function () {
                        var $attach = $(this);
                        var attachId = Number($attach.attr('id'));
                        var thumbnail = $attach.attr('fileurl');
                        var caption = $attach.attr('raw_caption');
                        $('<li></li>').addClass('thumbnail attached').data('id', attachId).css('background-image', 'url("' + thumbnail + '")').insertBefore($thumbnailsNewButton);
                        _set.attaches.push({
                            id: attachId,
                            caption: caption
                        });
                    });
                }
                if (Number($articleData.attr('user_id')) === 0) {
                    $option.find('li.anonym').addClass('active');
                }
                if (Number($articleData.attr('is_question')) === 1) {
                    $question.show();
                    $option.find('li.question').addClass('active');
                }
            }
            if (!$article) {
                if (_set.categories.length > 0) {
                    var $categoriesContainer = $('<p></p>').addClass('categories').prependTo($form);
                    _.each(_set.categories, function (category) {
                        var $categoryRadio = $('<input>').attr({
                            type: 'radio',
                            name: 'category_id',
                            id: 'category_' + category.id,
                            value: category.id
                        });
                        var $categoryLabel = $('<label></label>').attr({
                            for: 'category_' + category.id
                        }).text(category.name);
                        $categoriesContainer.append([
                            $categoryRadio,
                            $categoryLabel
                        ]);
                    });
                    if (_set.categoryId > 0) {
                        $categoriesContainer.find('[name="category_id"]').filter(function (idx, elem) {
                            return Number($(elem).val()) === _set.categoryId;
                        }).prop({ checked: true });
                    } else {
                        $categoriesContainer.find('[name="category_id"]').eq(0).prop({ checked: true });
                    }
                }
            }
        },
        addHashtagOnWriteArticleForm: function (hashtag) {
            var $writeForm = $articles.find('form.write');
            var $textarea = $writeForm.find('textarea[name="text"]');
            var text = (typeof hashtag !== 'undefined') ? (hashtag + ' ') : '#';
            $textarea.val($textarea.val() + text);
            $textarea.focus();
        },
        dragstartOnWriteArticleForm: function (event) {
            if (typeof window.FileReader === 'undefined' || !document.createElement('canvas').getContext) {
                return;
            }
            if (_.indexOf(event.originalEvent.dataTransfer.types, 'Files') === -1) {
                return;
            }
            _set.attachDragHoverCount++;
            $articles.find('form.write').addClass('dragover');
        },
        dragendOnWriteArticleForm: function (event) {
            if (typeof window.FileReader === 'undefined' || !document.createElement('canvas').getContext) {
                return;
            }
            if (_.indexOf(event.originalEvent.dataTransfer.types, 'Files') === -1) {
                return;
            }
            _set.attachDragHoverCount--;
            if (_set.attachDragHoverCount === 0) {
                $articles.find('form.write').removeClass('dragover');
            }
        },
        dropOnWriteArticleForm: function (event) {
            if (typeof window.FileReader === 'undefined' || !document.createElement('canvas').getContext) {
                return;
            }
            if (_.indexOf(event.originalEvent.dataTransfer.types, 'Files') === -1) {
                return;
            }
            _fn.changeAttachOnWriteArticleForm(event.originalEvent.dataTransfer.files);
        },
        addAttachOnWriteArticleForm: function () {
            if (typeof window.FileReader === 'undefined' || !document.createElement('canvas').getContext) {
                alert('이미지 첨부를 위해 최신 브라우저를 이용해주세요.');
                return;
            }
            var $writeForm = $articles.find('form.write');
            $writeForm.find('input[name="file"]').click();
        },
        changeAttachOnWriteArticleForm: function (files) {
            if (files.length === 0) {
                return;
            }
            if ((_set.attaches.length + files.length) > 20) {
                alert('이미지는 20장까지 첨부할 수 있습니다.');
                return;
            }
            var hasNotImage = false;
            _.each(files, function (file) {
                if (!file.type.match('image')) {
                    hasNotImage = true;
                }
            });
            if (hasNotImage) {
                alert('이미지만 첨부할 수 있습니다.');
                return;
            }
            if (_.indexOf(_set.attachUploadingStatus, 0) !== -1) {
                alert('이미지 첨부가 진행중입니다.');
                return;
            }
            _set.attachUploadingStatus = [];
            const $writeForm = $articles.find('form.write');
            const $thumbnails = $writeForm.find('> ol.thumbnails').show();
            const $thumbnailsNewButton = $thumbnails.find('> li.new');
            _.each(files, function (file, index) {
                _set.attachUploadingStatus.push(0);
                const $thumbnail = $('<li></li>').addClass('thumbnail loading').insertBefore($thumbnailsNewButton);
                const fileName = 'everytime-web-' + new Date().getTime().toString() + '.jpg';
                const loadImageOptions = {
                    canvas: true,
                    maxWidth: 1280
                };
                loadImage.parseMetaData(file, function (data) {
                    if (data.exif) {
                        loadImageOptions.orientation = data.exif.get('Orientation');
                    }
                    loadImage(file, function (canvas) {
                        if (!canvas.toDataURL || !canvas.toBlob) {
                            _set.attachUploadingStatus[index] = -1;
                            $thumbnail.remove();
                            return;
                        }
                        canvas.toBlob(function (blob) {
                            _fn.uploadAttachOnWriteArticleForm(index, blob, fileName, $thumbnail, canvas.toDataURL());
                        }, 'image/jpeg', 0.8);
                    }, loadImageOptions);
                });

            });
        },
        uploadAttachOnWriteArticleForm: function (index, file, filename, $thumbnail, thumbnail) {

            const $writeForm = $articles.find('form.write');
            if (_.indexOf(_set.attachUploadingStatus.slice(0, index), 0) !== -1) {
                setTimeout(function () {
                    _fn.uploadAttachOnWriteArticleForm(index, file, filename, $thumbnail, thumbnail);
                }, 100);
                return;
            }
            function uploadFail() {
                _set.attachUploadingStatus[index] = -1;
                $thumbnail.remove();
            }
            function uploadSuccess(attachId) {
                _set.attaches.push({
                    id: attachId,
                    caption: ''
                });
                _set.attachUploadingStatus[index] = 1;
                $thumbnail.removeClass('loading').addClass('attached').data('id', attachId).css('background-image', 'url("' + thumbnail + '")');
                $writeForm.find('input[name="file"]').val('');
            }

            $.ajax({
                url: '/save/board/article/attach',
                type: 'POST',
                contentType: false,
                data: {
                    board_id: _set.boardId,
                    file_name: filename,
                    file_size: file.size
                },
                success: function (data) {
                    var responseCode = $(data).find('response').text();
                    if (responseCode === '0' || responseCode === '-21' || responseCode === '-22') {
                        uploadFail();
                        return;
                    }
                    console.log(data);
                    var $attach = $(data).find('attach');
                    var $s3Provider = $(data).find('s3Provider');
                    var attachId = Number($attach.attr('id'));
                    var s3Path = $attach.attr('s3_path');
                    var s3ThumbnailPath = $attach.attr('s3_thumbnail_path');
                    var s3Provider = JSON.parse($s3Provider.attr('s3'));
                    var formData = new FormData();
                    formData.append('Content-Type', file.type);
                    formData.append('acl', s3Provider['acl']);
                    formData.append('success_action_status', s3Provider['success_action_status']);
                    formData.append('policy', s3Provider['policy']);
                    formData.append('X-amz-algorithm', s3Provider['X-amz-algorithm']);
                    formData.append('X-amz-credential', s3Provider['X-amz-credential']);
                    formData.append('X-amz-date', s3Provider['X-amz-date']);
                    formData.append('X-amz-expires', s3Provider['X-amz-expires']);
                    formData.append('X-amz-signature', s3Provider['X-amz-signature']);
                    formData.append('key', s3Path);
                    formData.append('file', file);
                    $.ajax({
                        url: 'https://' + s3Provider.bucket + '.s3.' + s3Provider.region + '.amazonaws.com/',
                        type: 'POST',
                        data: formData,
                        contentType: false,
                        processData: false,
                        success: function () {
                            $.ajax({
                                url: 'https://apigateway.everytime.kr/createThumbnail',
                                data: JSON.stringify({
                                    's3': {
                                        'srcKey': s3Path,
                                        'bucket': s3Provider.bucket,
                                        'dstKey': s3ThumbnailPath
                                    }
                                }),
                                method: 'post',
                                dataType: 'json',
                                success: function (createThumbnailResponse) {
                                    if (createThumbnailResponse === 'success') {
                                        uploadSuccess(attachId);
                                    } else {
                                        uploadFail();
                                    }
                                },
                                error: function () {
                                    uploadFail();
                                }
                            });
                        },
                        error: function () {
                            uploadFail();
                        }
                    });
                }
            });
        },
        showAttachThumbnailForm: function ($thumbnail) {
            var attach = _.find(_set.attaches, function (attach) {
                return attach.id === $thumbnail.data('id');
            });
            var $form = $container.find('#attachThumbnailForm');
            var $caption = $form.find('textarea[name="caption"]');
            $caption.val(attach.caption);
            $form.show();
            $form.off('submit');
            $form.on('submit', function () {
                attach.caption = $caption.val();
                $form.find('a.close').click();
                return false;
            });
            $form.find('.button[value="첨부 삭제"]').off('click');
            $form.find('.button[value="첨부 삭제"]').on('click', function () {
                if (!confirm('첨부된 이미지를 삭제하시겠습니까?')) {
                    return;
                }
                _set.removeAttachIds.push(attach.id);
                _set.attaches = _.reject(_set.attaches, function (i) {
                    return i.id === attach.id;
                });
                $thumbnail.remove();
                $form.find('a.close').click();
            });
            $form.find('a.close').off('click');
            $form.find('a.close').on('click', function () {
                $form.hide();
            });
        },
        writeArticle: function () {
            var $form = $articles.find('form.write');
            var $text = $form.find('textarea[name="text"]');
            var $option = $form.find('ul.option');
            var isAnonym = ($option.is(':has(li.anonym)') && $option.find('li.anonym').hasClass('active')) ? 1 : 0;
            var isQuestion = ($option.is(':has(li.question)') && $option.find('li.question').hasClass('active')) ? 1 : 0;
            const $files = $form.find('input[name="file"]');
            const form = new FormData();
            for (let i = 0; i < $files[0].files.length; i++) {
                form.append('files', $files[0].files[i]);
            }
            form.append("boardId", _set.boardId);
            form.append("text", $text.val());
            form.append("isAnonym", isAnonym);
            form.append("isQuestion", isQuestion);
            if ($text.val().replace(/ /gi, '') === '') {
                alert('내용을 입력해 주세요.');
                $text.focus();
                return false;
            }
            if (_set.type === 2) {
                const $title = $form.find('input[name="title"]');
                if ($title.val().replace(/ /gi, '') === '') {
                    alert('제목을 입력해 주세요.');
                    $title.focus();
                    return false;
                }
                form.append("title", $title.val());
            }
            if ($form.is(':has(input[name="article_id"])')) {
                parameters.article_id = $form.find('input[name="article_id"]').val();
                if (_set.removeAttachIds.length > 0) {
                    parameters.remove_attach_ids = JSON.stringify(_set.removeAttachIds);
                }
            }
            if ($form.is(':has(p.categories)')) {
                parameters.category_id = ($form[0].category_id || {}).value || 0;
            }
            if (isQuestion === 1 && !confirm('댓글이 달린 이후에는 글을 수정 및 삭제할 수 없습니다. 그래도 작성하시겠습니까?')) {
                return;
            }
            if (_set.isCommercial && !confirm(_set.placeholder)) {
                return;
            }
            $.ajax({
                url: '/save/board/article',
                type: 'POST',
                processData: false,
                contentType: false,
                data: form,
                success: function (data) {
                    var responseCode = $(data).find('response').text();
                    if (responseCode === '0') {
                        alert('글을 작성할 수 없습니다.');
                    } else if (responseCode == '-1') {
                        alert('너무 자주 글을 작성할 수 없습니다.');
                    } else if (responseCode === '-2') {
                        alert('내용을 입력해 주세요.');
                    } else if (responseCode === '-3') {
                        alert('지난 글과 다른 내용을 입력해 주세요.');
                    } else if (responseCode === '-4') {
                        alert('제목을 입력해 주세요.');
                    } else if (responseCode === '-5') {
                        alert('익명으로 글을 작성할 수 없습니다.');
                    } else if (responseCode === '-10') {
                        alert('게시판만 글을 수정할 수 있습니다.');
                    } else if (responseCode === '-11') {
                        alert('글을 수정할 수 없습니다.');
                    } else if (responseCode === '-13') {
                        alert('질문 글은 댓글이 달린 이후에는 수정할 수 없습니다.');
                    } else if (responseCode === '-14') {
                        alert('질문 글 설정은 다시 해제할 수 없습니다.');
                    } else {
                        _set.categoryId = 0;
                        var boardUrl = _fn.encodeUrl({ boardId: _set.boardId });
                        _fn.goRedirectContent(boardUrl);
                    }
                }
            });
        },
        searchArticle: function () {
            var $form = $container.find('#searchArticleForm');
            var $searchType = $form.find('select[name="search_type"]');
            var $keyword = $form.find('input[name="keyword"]');
            if ($keyword.val().replace(/ /gi, '').length < 2) {
                alert('검색어는 두 글자 이상 입력하세요.');
                $keyword.focus();
                return false;
            }
            var searchType = Number($searchType.val());
            var keyword = $keyword.val().replace(/[#?=&<>]/gi, '');
            var searchUrl;
            if (searchType === 3) {
                keyword = keyword.replace(/([^a-z0-9ㄱ-ㅎㅏ-ㅣ가-힣_])/gi, '');
                searchUrl = _fn.encodeUrl({ hashtag: keyword });
            } else if (searchType === 2) {
                searchUrl = _fn.encodeUrl({ title: keyword });
            } else if (searchType === 1) {
                searchUrl = _fn.encodeUrl({ text: keyword });
            } else {
                searchUrl = _fn.encodeUrl({ all: keyword });
            }
            _fn.goRedirectContent(searchUrl);
        },
        removeArticle: function ($article) {
            $.ajax({
                url: _apiServerUrl + '/remove/board/article',
                xhrFields: {withCredentials: true},
                type: 'POST',
                data: {
                    id: $article.data('id')
                },
                success: function (data) {
                    var responseCode = $(data).find('response').text();
                    if (responseCode === '1') {
                        $container.find('#goListButton').click();
                    } else if (responseCode === '-3') {
                        alert('질문 글은 댓글이 달린 이후에는 삭제할 수 없습니다.');
                    } else {
                        alert('삭제할 수 없습니다.');
                    }
                }
            });
        },
        abuseArticle: function ($article, reason) {
            $.ajax({
                url: _apiServerUrl + '/save/board/article/abuse',
                xhrFields: {withCredentials: true},
                type: 'POST',
                data: {
                    id: $article.data('id'),
                    reason: reason
                },
                success: function (data) {
                    var responseCode = $(data).find('response').text();
                    if (responseCode === '0') {
                        alert('신고할 수 없습니다.');
                    } else if (responseCode === '-1') {
                        alert('이미 신고한 글입니다.');
                    } else {
                        alert('신고하였습니다.');
                        location.reload();
                    }
                }
            });
        },
        voteArticle: function ($article) {
            var $vote = $article.find('a.article > ul.status > li.vote');
            if ($article.data('is_mine') === '1') {
                alert('자신의 글을 공감할 수 없습니다.');
                return false;
            }
            if (!confirm('이 글에 공감하십니까?')) {
                return false;
            }
            if (!_set.isUser) {
                alert('로그인 후 가능합니다.');
                return false;
            }
            $.ajax({
                url: '/save/board/article/vote',
                type: 'POST',
                data: {
                    articleId: $article.data('id'),
                    vote: '1'
                },
                success: function (data) {
                    var response = Number($('response', data).text());
                    if (response === 0) {
                        alert('공감할 수 없습니다.');
                    } else if (response === -1) {
                        alert('이미 공감하였습니다.');
                    } else if (response === -2) {
                        alert('오래된 글은 공감할 수 없습니다.');
                    } else {
                        $vote.text(response);
                    }
                }
            });
        },
        scrapArticle: function ($article) {
            var $scrap = $article.find('ul.status > li.scrap');
            if (!confirm('이 글을 스크랩하시겠습니까?')) {
                return false;
            }
            if (!_set.isUser) {
                alert('로그인 후 가능합니다.');
                return false;
            }
            $.ajax({
                url: '/save/board/article/scrap',
                type: 'POST',
                data: {
                    articleId: $article.data('id')
                },
                success: function (data) {
                    const response = Number(data);
                    if (response === 0) {
                        alert('스크랩할 수 없습니다.');
                    } else if (response === -1) {
                        alert('존재하지 않는 글입니다.');
                    } else if (response === -2) {
                        alert('이미 스크랩하였습니다.');
                    } else if (response === -3) {
                        alert('내가 쓴 글은 스크랩할 수 없습니다.');
                    } else {
                        $scrap.text(response);
                    }
                }
            });
        },
        removeScrap: function ($article) {
            if (!confirm('스크랩을 취소하시겠습니까?')) {
                return false;
            }
            $.ajax({
                url: '/remove/board/article/scrap',
                type: 'POST',
                data: {
                    articleId: $article.data('id')
                },
                success: function (data) {
                    const response = Number(data);
                    if (response === 0) {
                        alert('취소할 수 없습니다.');
                    } else if (response === -1) {
                        alert('존재하지 않는 글입니다.');
                    } else {
                        $article.remove();
                    }
                }
            });
        },
        setNoticeArticle: function ($article) {
            if (!confirm('이 글을 공지로 설정하시겠습니까?')) {
                return false;
            }
            $.ajax({
                url: _apiServerUrl + '/update/board/notice',
                xhrFields: {withCredentials: true},
                type: 'POST',
                data: {
                    id: $article.data('id')
                },
                success: function (data) {
                    var response = Number($('response', data).text());
                    if (response === 1) {
                        alert('공지로 설정하였습니다.');
                    } else {
                        alert('설정에 실패하였습니다.');
                    }
                }
            });
        },
        writeComment: function ($form) {
            var $article = $form.parents('article');
            var $text = $form.find('input[name="text"]');
            var $option = $form.find('ul.option');
            var isAnonym = ($option.is(':has(li.anonym)') && $option.find('li.anonym').hasClass('active')) ? 1 : 0;
            if ($text.val().replace(/ /gi, '') === '') {
                alert('내용을 입력해 주세요.');
                $text.focus();
                return false;
            }
            var params = {
                text: $text.val(),
                isAnonym: isAnonym
            };
            if ($form.data('parentId')) {
                params.commentId = $form.data('parentId');
            } else {
                params.articleId = $article.data('id');
            }
            $.ajax({
                url: '/save/board/comment',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(params),
                success: function (data) {
                    if (data === 0 || data === -3) {
                        alert('댓글을 작성할 수 없습니다.');
                    } else if (data === -1) {
                        alert('너무 자주 댓글을 작성할 수 없습니다.');
                    } else if (data === -2) {
                        alert('내용을 입력해 주세요.');
                    } else {
                        location.reload();
                    }
                }
            });
        },
        // todo 대댓글 문제 해결중
        createChildCommentForm: function ($comment) {
            let $commentForm = $articles.find('> article > div.comments > form.writecomment').filter(function () {
                return $(this).data('parentId') === $comment.data('id');
            });
            if ($commentForm.length === 0) {
                $commentForm = $articles.find('> article > div.comments > form.writecomment:not(.child)').clone().addClass('child').data('parentId', $comment.data('id'));
                $commentForm.find('input[name="text"]').attr('placeholder', '대댓글을 입력하세요.');
                console.log($articles.find('> article > div.comments > article.child'));
                let $beforeComment = $articles.find('> article > div.comments > article.child').filter(function () {
                    return $(this).data('parentId') === $comment.data('id');
                }).last();
                if ($beforeComment.length === 0) {
                    $beforeComment = $articles.find('> article > div.comments > article.parent').filter(function () {
                        return $(this).data('id') === $comment.data('id');
                    });
                }
                $commentForm.insertAfter($beforeComment);
            }
            $commentForm.find('input[name="text"]').focus();
        },
        voteComment: function ($comment) {
            var $vote = $comment.find('ul.status > li.vote');
            if ($comment.data('is_mine') === '1') {
                alert('내가 쓴 댓글은 공감할 수 없습니다.');
                return false;
            }
            if (!confirm('이 댓글에 공감하십니까?')) {
                return false;
            }
            if (!_set.isUser) {
                alert('로그인 후 가능합니다.');
                return false;
            }
            $.ajax({
                url: _apiServerUrl + '/save/board/comment/vote',
                xhrFields: {withCredentials: true},
                type: 'POST',
                data: {
                    id: $comment.data('id'),
                    vote: '1'
                },
                success: function (data) {
                    var response = Number($('response', data).text());
                    if (response === -1) {
                        alert('이미 공감한 댓글입니다.');
                    } else if (response === -2) {
                        alert('오래된 댓글은 공감할 수 없습니다.');
                    } else if (response <= 0) {
                        alert('공감할 수 없습니다.');
                    } else {
                        $vote.text(response).show();
                    }
                }
            });
        },
        removeComment: function ($comment) {
            $.ajax({
                url: _apiServerUrl + '/remove/board/comment',
                xhrFields: {withCredentials: true},
                type: 'POST',
                data: {
                    id: $comment.data('id')
                },
                success: function (data) {
                    response = Number($('response', data).text());
                    if (Number($('response', data).text())) {
                        $comment.remove();
                    } else {
                        alert('삭제할 수 없습니다.');
                    }
                }
            });
        },
        abuseComment: function ($comment, reason) {
            $.ajax({
                url: _apiServerUrl + '/save/board/comment/abuse',
                xhrFields: {withCredentials: true},
                type: 'POST',
                data: {
                    id: $comment.data('id'),
                    reason: reason
                },
                success: function (data) {
                    var responseCode = $(data).find('response').text();
                    if (responseCode === '0') {
                        alert('신고할 수 없습니다.');
                    } else if (responseCode === '-1') {
                        alert('이미 신고한 댓글입니다.');
                    } else {
                        alert('신고하였습니다.');
                        location.reload();
                    }
                }
            });
        }
    };
    _fn.initiate();
});