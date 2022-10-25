package project.everytime.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.everytime.client.message.service.MessageService;
import project.everytime.client.user.dto.LoginUser;
import project.everytime.login.Login;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final MessageService messageService;

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

    @GetMapping("/message/item/{itemId}")
    public String message(@PathVariable Long itemId, Model model) {
        model.addAttribute("boxId", 0);
        return "book/message";
    }

    @GetMapping("/message/box/{boxId}")
    public String messageBox(@PathVariable Long boxId, @Login LoginUser loginUser) {
        return "book/message";
    }
}
