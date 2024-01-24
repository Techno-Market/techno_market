package com.techno_market.techno_market.domain.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/l")
    public String login() {
        return "login";
    }

    @GetMapping("/s")
    public String signup() {
        return "signup";
    }

    @GetMapping("/is")
    public String is() {
        return "id_search";
    }

    @GetMapping("/isr")
    public String isr() {
        return "id_search_result";
    }

    @GetMapping("/ps")
    public String ps() {
        return "password_search";
    }

    @GetMapping("/psr")
    public String psr() {
        return "password_search_modify";
    }

    @GetMapping("/spf")
    public String spf() {
        return "sales_post_form";
    }

    @GetMapping("/spd")
    public String spd() {
        return "sales_post_detail";
    }

    @GetMapping("/c")
    public String c() {
        return "chat";
    }
}
