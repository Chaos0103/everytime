bookstore.my = {
    loaded: false,
    loadedLength: 0,
    init: function ($itemsDiv) {
        $itemsDiv.empty();
        bookstore.my.loadItems($itemsDiv);
    },
    loadItems: function ($itemsDiv) {
        bookstore.my.loaded = false;
        bookstore.fn.findItemList(1, null, null, bookstore.my.loadedLength, function (response) {
            if (!response || response.error) {
                $('<a></a>').addClass('item empty').text('책 목록을 불러올 수 없습니다.').appendTo($itemsDiv);
                return false;
            }
            _.each(response, function (item) {
                const $item = $('<a></a>').addClass('item').data('item', item).attr({
                    href: '/book/view/' + item.id
                });
                if (item.soldout) {
                    $item.addClass('soldout');
                }
                const $thumb = $('<div></div>').addClass('thumb').appendTo($item);
                if (item.coverImage) {
                    $thumb.css({
                        'background-image': 'url("/file/' + item.coverImage + '")'
                    });
                }
                const book = item.book;
                $('<h2></h2>').text(book.title).appendTo($item);
                var $detailsAuthor = $('<p></p>').addClass('details author').appendTo($item);
                $('<span></span>').text(book.author + ' 지음').appendTo($detailsAuthor);
                var $detailsPublisher = $('<p></p>').addClass('details publisher').appendTo($item);
                $('<span></span>').text(book.publisher).appendTo($detailsPublisher);
                var $price = $('<p></p>').addClass('price').appendTo($item);
                $('<span></span>').addClass('selling').text(bookstore.fn.formatPrice(item.price)).appendTo($price);
                if (book.price) {
                    $('<span></span>').addClass('original').text(bookstore.fn.formatPrice(book.price)).appendTo($price);
                }
                $('<hr>').appendTo($item);
                $item.appendTo($itemsDiv);
            });
            if (!response.length && !bookstore.my.loadedLength) {
                $('<a></a>').addClass('item empty').text('검색된 책이 없습니다.').appendTo($itemsDiv);
                return false;
            }
            bookstore.my.loaded = true;
            bookstore.my.loadedLength += response.length;
        });
    }
};

$().ready(function () {
    var $itemsDiv = $('#items');
    bookstore.my.init($itemsDiv);

    $(window)
        .on('scroll', function () {
            var scrollTop = $(window).scrollTop();
            if (!bookstore.my.loaded || bookstore.my.loadedLength % 20 !== 0) {
                return false;
            }
            if (scrollTop < ($(document).height() - $(window).height() - 300)) {
                return false;
            }
            bookstore.my.loadItems($itemsDiv);
        });
});