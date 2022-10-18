package project.everytime.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class JoinController {

    @GetMapping
    public String register() {
        return "common/join/register";
    }

    @PostMapping("/agreement")
    public String agreement(
            @RequestParam Integer enterYear,
            @RequestParam Long schoolId,
            Model model) {
        log.debug("enterYear={}, schoolId={}", enterYear, schoolId);
        model.addAttribute("enterYear", enterYear);
        model.addAttribute("schoolId", schoolId);
        return "common/join/agreement";
    }

    @PostMapping("/info")
    public String info(
            @RequestParam Integer enterYear,
            @RequestParam Long schoolId,
            @RequestParam Integer agreementAd,
            Model model) {
        model.addAttribute("agreementAd", agreementAd);
        model.addAttribute("enterYear", enterYear);
        model.addAttribute("schoolId", schoolId);
        return "common/join/info";
    }

    @PostMapping("/pass")
    public String pass() {
        return "common/join/pass";
    }
}
