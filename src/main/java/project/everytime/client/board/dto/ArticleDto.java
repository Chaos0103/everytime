package project.everytime.client.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String text;
    private boolean anonymous;
    private boolean question;
    private List<MultipartFile> files = new ArrayList<>();
}
