package com.techno_market.techno_market.domain.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    //테스트
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "test";
    }
}
