package project.everytime.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/page")
public class PageController {

    @GetMapping("/serviceagreement")
    public String serviceAgreement() {
        return "page/serviceagreement";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "page/privacy";
    }

    @GetMapping("/rules")
    public String rules() {
        return "page/rules";
    }

    @GetMapping("/faq")
    public String faq() {
        return "page/faq";
    }
}
