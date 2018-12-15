package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public  String getMain(
            @AuthenticationPrincipal User user) {
        if(user != null){
            return "main";
        }

        return "greeting";
    }


}
