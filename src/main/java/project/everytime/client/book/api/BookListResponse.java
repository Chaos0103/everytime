package project.everytime.client.book.api;

import lombok.Data;
import project.everytime.client.book.Book;

@Data
public class BookListResponse {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String pubDate;
    private Integer price;

    public BookListResponse(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.pubDate = book.getPubDate();
        this.price = book.getPrice();
    }
}
