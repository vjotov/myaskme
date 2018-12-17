package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.domain.dto.CardDto;
import com.jotov.myaskme.repos.UserRepo;
import com.jotov.myaskme.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @AuthenticationPrincipal User currentUser,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            Model model
    ) {
        if(currentUser != null){
            loadCards(model, currentUser,null);
            //loadCards(pageable, model, currentUser,null);
            return "main";
        }

        return "greeting";
    }

    @PostMapping("/ask")
    public String ask(
            @AuthenticationPrincipal User currentUser,
            @RequestParam String question,
            @RequestParam(defaultValue = "1") Long receiver_id,
            //@PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            Model model
    ) {
        //TODO: move this ro UserService

        User receiver = userRepo.findById(receiver_id).get();
        if (receiver != null){
            Card card = new Card(question, currentUser, receiver);
            cardService.save(card);
        } else {
            model.addAttribute("message","Cannot post your question");
        }
        loadCards(model, currentUser,null);
        return "main";
    }

    private void loadCards( Model model, User currentUser, User receiver) {
    //private void loadCards(Pageable pageable, Model model, User currentUser, User receiver) {
        Iterable<Card> page ;
        if (receiver == null) {
            page = cardService.cardListAll(currentUser);
            //page = cardService.cardListAll(pageable, currentUser);
        } else {
            page = cardService.cardListForUserReceiver(currentUser, receiver);
            //page = cardService.cardListForUserReceiver(pageable, currentUser, receiver);
        }
        model.addAttribute("page", page);
    }
}
