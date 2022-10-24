package project.everytime.client.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookAddDto {

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String pubDate;
    private Integer price;
}
