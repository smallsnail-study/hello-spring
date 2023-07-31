package io.devlabs.intro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 도메인 첫 화면
    public String home() {
        return "home";
    }
}
