package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.domain.dto.CardDto;
import com.jotov.myaskme.repos.UserRepo;
import com.jotov.myaskme.service.CardService;
import com.jotov.myaskme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    CardService cardService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public  String getMain(
            @AuthenticationPrincipal User currentUser,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            Model model
    ) {
        if(currentUser != null){
            model.addAttribute("page", loadCards(currentUser, null));
            return "main";
        }

        return "greeting";
    }

    @PostMapping("/ask")
    public String ask(
            @AuthenticationPrincipal User currentUser,
            @RequestParam String question,
            @RequestParam Long receiver_id,
            //@RequestParam User reciever,
            //@PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            Model model
    ) {
        //TODO: move this ro UserService

        User reciever = userRepo.findById(receiver_id).get();

        if (reciever != null){
            Card card = new Card(question, currentUser, reciever);
            cardService.save(card);
        } else {
            model.addAttribute("message","Cannot post your question");
        }
        model.addAttribute("page", loadCards(currentUser, null));
        return "main";
    }

    @PostMapping("answer/{card}")
    public String answer(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("answer_text") String answer,
            @PathVariable Card card,
            Model model
    ) {
        if(!card.getReceiver().equals(currentUser)) {
            model.addAttribute("message", "You cannot answer to this question");
        } else if (! card.getAnswer().isEmpty()) {
            model.addAttribute("message", "You cannot update your answer");
        } else {
            cardService.saveAnswer(card, answer);
        }
        model.addAttribute("page", loadCards(currentUser, null));
        return "main";
    }

    @GetMapping("channel/{userChannel}")
    public String viewChannel(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User userChannel,
            Model model
    ) {
        model.addAttribute("userChannel", userChannel);
        model.addAttribute("page", loadCards(currentUser, userChannel));
        model.addAttribute("isCurrentUser", currentUser.equals(userChannel));
        return "main";
    }

    private Iterable<Card> loadCards( User currentUser, User receiver) {
    //private void loadCards(Pageable pageable, Model model, User currentUser, User receiver) {
        if (receiver == null) {
            return cardService.cardListAll(currentUser);
            //page = cardService.cardListAll(pageable, currentUser);
        } else {
            return cardService.cardListForUserReceiver(currentUser, receiver);
            //page = cardService.cardListForUserReceiver(pageable, currentUser, receiver);
        }
    }
}
