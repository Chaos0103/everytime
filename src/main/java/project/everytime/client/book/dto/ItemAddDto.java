package project.everytime.client.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemAddDto {

    private int price;
    private String statusNote;
    private String statueDamage;
    private int meansDelivery;
    private int meansDirect;
    private String comment;
    private String location;
    private MultipartFile coverImage;
    private List<MultipartFile> images = new ArrayList<>();
}
