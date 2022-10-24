package project.everytime.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    @GetMapping
    public String bookPage() {
        return "book/book";
    }

    @GetMapping("/sell")
    public String sellPage() {
        return "book/sell";
    }

    @GetMapping("/view/{itemId}")
    public String view(@PathVariable Long itemId) {
        return "book/view";
    }

    @GetMapping("/my")
    public String myPage() {
        return "book/my";
    }

    @GetMapping("/messagebox")
    public String messagebox() {
        return "book/messagebox";
    }
}
