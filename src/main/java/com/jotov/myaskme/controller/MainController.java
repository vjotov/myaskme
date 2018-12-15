package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.repos.UserRepo;
import com.jotov.myaskme.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    CardService cardService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public  String getMain(
            @AuthenticationPrincipal User user) {
        if(user != null){
            return "main";
        }

        return "greeting";
    }

    @PostMapping("/ask")
    public String ask(
            @AuthenticationPrincipal User user,
            @RequestParam String question,
            @RequestParam(value = "1") Long receiver_id,
            Model model
    ) {
        //TODO: move this ro UserService
        User receiver = userRepo.findById(receiver_id).orElse(null);
        if (receiver != null){
            Card card = new Card(question, user, receiver);
            cardService.save(card);
        } else {
            model.addAttribute("message","Cannot post your question");
        }
        return "main";
    }
}
