package project.everytime.client.book.api;

import lombok.Data;
import project.everytime.client.UploadFile;
import project.everytime.client.book.Book;
import project.everytime.client.book.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ItemListResponse {

    private Long id;
    private String coverImage;
    private String createdDate;
    private Integer price;
    private int soldOut;
    private String statusDamage;
    private String statusNote;

    private BookListResponse book;

    public ItemListResponse(Item item) {
        this.id = item.getId();
        this.price = item.getPrice();
        this.coverImage = getCoverImage(item.getCoverImage());
        this.createdDate = formatDate(item.getCreatedDate());
        this.soldOut = item.getSoldOut();
        this.statusDamage = item.getStatusDamage();
        this.statusNote = item.getStatusNote();
        this.book = getBook(item.getBook());
    }

    private String getCoverImage(UploadFile coverImage) {
        return coverImage != null ? coverImage.getStoreFileName() : null;
    }

    private String formatDate(LocalDateTime createdDate) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
    }

    private BookListResponse getBook(Book book) {
        return new BookListResponse(book);
    }
}
