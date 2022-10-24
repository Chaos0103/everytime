package project.everytime.client.controller.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
public class KakaoApiController {

    @Value("${kakao.key}")
    private String key;
    private final String url = "https://dapi.kakao.com/v3/search/book";

    @GetMapping("/kakao/bookList")
    public List<BookResponse> bookList(@RequestParam("keyword") String query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + key);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        URI targetUrl = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("query", query)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        ResponseEntity<ResponseBody> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, ResponseBody.class);
        ResponseBody body = result.getBody();
        List<DocumentResponse> documents = body.getDocuments();
        return documents.stream()
                .map(BookResponse::new)
                .toList();
    }

    @Data
    static class ResponseBody {
        private List<DocumentResponse> documents;
    }

    @Data
    static class DocumentResponse {
        private String[] authors;
        private String thumbnail;
        private String isbn;
        private Integer price;
        private String datetime;
        private String publisher;
        private String title;
    }

    @Data
    static class BookResponse {
        private String author;
        private String image;
        private String isbn;
        private Integer price;
        private String pubdate;
        private String publisher;
        private String title;

        public BookResponse(DocumentResponse document) {
            this.author = document.getAuthors()[0];
            this.image = document.getThumbnail();
            this.isbn = document.getIsbn().split(" ")[0];
            this.price = document.getPrice();
            this.pubdate = document.getDatetime().substring(0, 10).replaceAll("-", "");
            this.publisher = document.getPublisher();
            this.title = document.getTitle();
        }
    }
}
