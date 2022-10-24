bookstore.index = {
    loaded: false,
    loadedLength: 0,
    parseParams: function (url) {
        const urlParams = bookstore.fn.parseUrlParams(url);
        const keyword = urlParams.keyword ? urlParams.keyword.trim() : '';
        return {
            keyword: keyword
        };
    },
    goLinkContent: function ($searchForm, $itemsDiv, that, event) {
        event.stopPropagation();
        if (typeof history.pushState === 'undefined') {
            return false;
        }
        const url = $(that).attr('href');
        event.preventDefault();
        history.pushState(null, null, url);
        const params = bookstore.index.parseParams(url);
        bookstore.index.loadItems($searchForm, $itemsDiv, params, 0);
    },
    goRedirectContent: function ($searchForm, $itemsDiv, url) {
        if (typeof history.pushState === 'undefined') {
            location.href = url;
            return false;
        }
        history.pushState(null, null, url);
        const params = bookstore.index.parseParams(url);
        bookstore.index.loadItems($searchForm, $itemsDiv, params, 0);
        return false;
    },
    init: function ($searchForm, $itemsDiv) {
        const $header = $('<div></div>').addClass('header');
        $('<h1></h1>').html('&nbsp;').appendTo($header);
        const $filter = $('<div></div>').addClass('filter').appendTo($header);
        const $filterCampus = $('<a></a>').addClass('campus').appendTo($filter);
        $('<span></span>').addClass('text').text(bookstore.data.user.campus_full_name).appendTo($filterCampus);
        $('<span></span>').addClass('icons arrowdown-gray-14').appendTo($filterCampus);
        $header.appendTo($itemsDiv);
        const params = bookstore.index.parseParams();
        bookstore.index.loadItems($searchForm, $itemsDiv, params, 0);
    },
    loadItems: function ($searchForm, $itemsDiv, params, start) {
        $('#loading').show();
        $searchForm.find('input[name="keyword"]').val(params.keyword);
        bookstore.index.loaded = false;
        if (!start) {
            bookstore.index.loadedLength = 0;
            $itemsDiv.find('a.item').remove();
        }
        if (typeof Storage !== 'undefined') {
            const previouspageCache = sessionStorage.getItem('previouspage');
            if (!start && previouspageCache && previouspageCache.substr(0, 5) === '/view') {
                const key = location.pathname + location.search;
                const itemlistLengthCache = sessionStorage.getItem('itemlist_length_' + key);
                const itemlistHtmlCache = sessionStorage.getItem('itemlist_html_' + key);
                const itemlistScrollCache = sessionStorage.getItem('itemlist_scroll_' + key);
                if (itemlistLengthCache && itemlistHtmlCache && itemlistHtmlCache) {
                    $itemsDiv.html(itemlistHtmlCache);
                    bookstore.index.loaded = true;
                    bookstore.index.loadedLength = Number(itemlistLengthCache) || 0;
                    $('#loading').hide();
                    $(window).scrollTop(Number(itemlistScrollCache) || 0);
                    return;
                }
            }
        }
        bookstore.fn.findItemList(0, params.keyword, null, start, function (response) {
            $('#loading').hide();
            if (response.error) {
                let message = '책 목록을 불러올 수 없습니다.';
                if (response.error === 'TooManyBooks') {
                    message = '정확한 검색 결과를 위해<br>구체적인 책 제목을 입력하거나<br>ISBN 검색을 이용해주세요.';
                }
                $('<a></a>').addClass('item empty').html(message).appendTo($itemsDiv);
                return false;
            }
            const $header = $itemsDiv.find('> div.header');
            const $title = $header.find('> h1');
            const $filter = $header.find('> div.filter');
            if (params.keyword) {
                let titleText = '검색 결과: ';
                if (response.length >= 40) {
                    titleText += '40개 이상';
                } else {
                    titleText += response.length + '개';
                }
                $title.text(titleText);
            } else {
                $title.text('최근 올라온 책');
            }
            _.each(response, function (item) {
                const $item = $('<a></a>').addClass('item').data('item', item).attr({
                    href: '/book/view/' + item.id
                });
                if (item.soldOut) {
                    $item.addClass('soldout');
                }
                const $thumb = $('<div></div>').addClass('thumb').appendTo($item);
                if (item.coverImage) {
                    $thumb.css({
                        'background-image': 'url("' + '/file/' + item.coverImage + '")'
                    });
                }
                const book = item.book;
                $('<h2></h2>').text(book.title).appendTo($item);
                const $detailsAuthor = $('<p></p>').addClass('details author').appendTo($item);
                $('<span></span>').text(book.author + ' 지음').appendTo($detailsAuthor);
                const $detailsPublisher = $('<p></p>').addClass('details publisher').appendTo($item);
                $('<span></span>').text(book.publisher).appendTo($detailsPublisher);
                const $price = $('<p></p>').addClass('price').appendTo($item);
                $('<span></span>').addClass('selling').text(bookstore.fn.formatPrice(item.price)).appendTo($price);
                if (book.price) {
                    $('<span></span>').addClass('original').text(bookstore.fn.formatPrice(book.price)).appendTo($price);
                }
                $('<hr>').appendTo($item);
                $item.appendTo($itemsDiv);
            });
            if (!response.length) {
                if (!bookstore.index.loadedLength) {
                    $('<a></a>').addClass('item empty').text('검색된 책이 없습니다.').appendTo($itemsDiv);
                }
                return false;
            }
            bookstore.index.loaded = true;
            bookstore.index.loadedLength += response.length;
            if (typeof Storage !== 'undefined') {
                const key = location.pathname + location.search;
                sessionStorage.setItem('itemlist_length_' + key, bookstore.index.loadedLength);
                sessionStorage.setItem('itemlist_html_' + key, $itemsDiv.html());
            }
        });
    }
};

$().ready(function () {
    const $searchForm = $('#search > form');
    const $itemsDiv = $('#items');
    bookstore.index.init($searchForm, $itemsDiv);
    setTimeout(function () {
        $(window).on('popstate', function () {
            const params = bookstore.index.parseParams();
            bookstore.index.loadItems($searchForm, $itemsDiv, params, 0);
        });
    }, 0);

    $(window)
        .on('scroll', function () {
            const scrollTop = $(window).scrollTop();
            if (typeof Storage !== 'undefined') {
                const key = location.pathname + location.search;
                sessionStorage.setItem('itemlist_scroll_' + key, scrollTop);
            }
            if (!bookstore.index.loaded || bookstore.index.loadedLength % 40 !== 0) {
                return false;
            }
            if (scrollTop < ($(document).height() - $(window).height() - 300)) {
                return false;
            }
            const params = bookstore.index.parseParams();
            bookstore.index.loadItems($searchForm, $itemsDiv, params, bookstore.index.loadedLength);
        });

    $searchForm
        .on('submit', function () {
            const $keywordInput = $searchForm.find('input[name="keyword"]');
            const keyword = $keywordInput.val().trim();
            if (keyword.length < 2) {
                alert('검색어를 두 글자 이상 입력하세요!');
                $keywordInput.focus();
                return false;
            }
            $keywordInput.blur();
            const urlParams = bookstore.fn.parseUrlParams();
            const url = '?keyword=' + encodeURIComponent(keyword);
            bookstore.index.goRedirectContent($searchForm, $itemsDiv, url);
            return false;
        })
        .on('click', 'div.searchbutton', function () {
            $searchForm.submit();
        });

    $itemsDiv
        .on('click', '> div.header > div.filter > a.campus', function () {
            alert('거래 사기를 방지하고, 안전한 직거래 환경을 제공하고자\n타 대학·캠퍼스 학생과의 거래 기능이 종료되었습니다.');
        })
        .on('click', '> div.popup > input.button.close', function (event) {
            $itemsDiv.find('div.popup').hide().remove();
        })
        .on('click', '> div.popup-filter > ol.menu > li > a', function (event) {
            bookstore.index.goLinkContent($searchForm, $itemsDiv, this, event);
            $itemsDiv.find('div.popup-filter').hide().remove();
        });
});