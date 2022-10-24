package project.everytime.client.controller.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.everytime.admin.lecture.api.LectureListResponse;
import project.everytime.admin.lecture.service.LectureQueryService;
import project.everytime.client.book.api.ItemListResponse;
import project.everytime.client.book.api.ItemViewResponse;
import project.everytime.client.book.dto.BookAddDto;
import project.everytime.client.book.dto.ItemAddDto;
import project.everytime.client.book.service.ItemQueryService;
import project.everytime.client.book.service.ItemService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.login.Login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final ItemService itemService;
    private final ItemQueryService itemQueryService;
    private final LectureQueryService lectureQueryService;

    @GetMapping("/find/itemList")
    public List<ItemListResponse> bookList(
            @RequestParam("mine") Integer mine,
            @RequestParam("keyword") String keyword,
            @RequestParam("start") Integer start,
            @Login LoginUser loginUser) {
        //40개
        List<ItemListResponse> itemListResponses = itemQueryService.bookList(loginUser.getId(), mine, keyword, start);
        return itemListResponses;
    }

    @GetMapping("/find/lecturelist.json")
    public List<LectureListResponse> lectureList(
            @RequestParam("field") String field,
            @RequestParam("keyword") String keyword,
            @Login LoginUser loginUser) {
        log.debug("field={}, keyword={}", field, keyword);
        List<LectureListResponse> lectureListResponses = lectureQueryService.lectureList(loginUser.getSchoolId(), field, keyword);
        return lectureListResponses;
    }

    @PostMapping("/save/item")
    public Long addBook(ItemAddRequest request, @Login LoginUser loginUser) throws IOException {

        MultipartFile coverImage = null;
        List<MultipartFile> images = new ArrayList<>();
        if (request.getImages().size() == 1) {
            coverImage = request.getImages().get(0);
        } else if (request.getImages().size() > 1) {
            coverImage = request.getImages().get(0);
            for (int i = 1; i < request.getImages().size(); i++) {
                images.add(request.getImages().get(i));
            }
        }
        log.debug(String.valueOf(request));
        BookAddDto bookAddDto = new BookAddDto(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getPublisher(), request.getPubDate(), request.getPrice());
        ItemAddDto itemAddDto = new ItemAddDto(request.getSellPrice(), request.getStatusNote(), request.getStatusDamage(), request.getMeansDelivery(), request.getMeansDirect(), request.getComment(), request.getLocation(), coverImage, images);
        Long bookId = itemService.addItem(loginUser.getId(), request.getLectureId(), bookAddDto, itemAddDto);
        return bookId;
    }

    @GetMapping("/find/itemview")
    public ItemViewResponse viewBook(@RequestParam("itemId") Long itemId, @Login LoginUser loginUser) {
        ItemViewResponse bookView = itemQueryService.findItem(itemId, loginUser.getId());
        return bookView;
    }

    @PostMapping("/edit/itemPrice")
    public Long editItemComment(@RequestBody ItemPriceEditRequest request) {
        log.debug("itemId={}, itemPrice={}", request.getId(), request.getPrice());
        Long itemId = itemService.editItemPrice(request.getId(), request.getPrice());
        return itemId;
    }

    @PostMapping("/edit/itemComment")
    public Long editItemPrice(@RequestBody ItemCommentEditRequest request) {
        log.debug("itemId={}, itemComment={}", request.getId(), request.getComment());
        Long itemId = itemService.editItemComment(request.getId(), request.getComment());
        return itemId;
    }

    @PostMapping("/soldout")
    public Long soldOut(@RequestBody ItemRequest request) {
        log.debug("itemId={}", request.getItemId());
        Long itemId = itemService.soldOut(request.getItemId());
        return itemId;
    }

    @Data
    static class ItemAddRequest {
        private String isbn;
        private String title;
        private String author;
        private String publisher;
        private Integer price;
        private String pubDate;
        private Long lectureId;
        private String statusNote;
        private String statusDamage;
        private List<MultipartFile> images = new ArrayList<>();
        private Integer sellPrice;
        private int meansDelivery;
        private int meansDirect;
        private String comment;
        private String location;
    }

    @Data
    static class ItemRequest {
        private Long itemId;
    }

    @Data
    static class ItemPriceEditRequest {
        private Long id;
        private Integer price;
    }

    @Data
    static class ItemCommentEditRequest {
        private Long id;
        private String comment;
    }
}
