package project.everytime.client.book.api;

import lombok.Data;
import project.everytime.client.UploadFile;
import project.everytime.client.book.Book;
import project.everytime.client.book.Item;
import project.everytime.client.book.ItemImage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ItemViewResponse {

    private Long id;
    private Boolean isMine;
    private Integer soldOut;
    private String statusNote;
    private String statusDamage;
    private Integer price;
    private Integer meansDelivery;
    private Integer meansDirect;
    private String comment;
    private String createdDate;
    private String coverImage;
    private List<String> images;

    private String location;
    private BookViewResponse book;

    public ItemViewResponse(Item item, Long userId) {
        this.id = item.getId();
        this.isMine = userIdEq(item.getUser().getId(), userId);
        this.soldOut = item.getSoldOut();
        this.statusNote = item.getStatusNote();
        this.statusDamage = item.getStatusDamage();
        this.price = item.getPrice();
        this.meansDelivery = item.getMeansDelivery();
        this.meansDirect = item.getMeansDirect();
        this.comment = item.getComment();
        this.createdDate = formatDate(item.getCreatedDate());
        this.coverImage = getCoverImage(item.getCoverImage());
        this.images = getImages(item.getImages());
        this.location = item.getLocation();
        this.book = getBook(item.getBook());
    }

    private Boolean userIdEq(Long itemUserId, Long userId) {
        return itemUserId.equals(userId);
    }

    private String getCoverImage(UploadFile coverImage) {
        return coverImage != null ? coverImage.getStoreFileName() : null;
    }

    private String formatDate(LocalDateTime createdDate) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
    }

    private List<String> getImages(List<ItemImage> images) {
        return images.stream()
                .map(bookImage -> bookImage.getUploadFile().getStoreFileName())
                .toList();
    }

    private BookViewResponse getBook(Book book) {
        return new BookViewResponse(book);
    }
}
