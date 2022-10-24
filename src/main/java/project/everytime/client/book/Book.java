package project.everytime.client.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everytime.client.TimeBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "book_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, length = 13)
    private String isbn;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 50)
    private String author;
    @Column(nullable = false, length = 20)
    private String publisher;
    @Column(nullable = false, length = 10)
    private String pubDate;
    @Column(nullable = false)
    private Integer price;

    public Book(String isbn, String title, String author, String publisher, String pubDate, Integer price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.price = price;
    }

    //== 비즈니스 로직 ==//
    public void change(String title, String author, String publisher, String pubDate, Integer price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.price = price;
    }
}
