package com.jotov.myaskme.controller;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.dto.CardDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public  String getMain(
            @AuthenticationPrincipal User currentUser,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable
    ) {


        if(currentUser != null){
            Page<CardDto> page = cardService.cardListAll(pageable, currentUser);

            model.addAttribute("page", page);
            return "main";
        }

        return "greeting";
    }

    @PostMapping("/ask")
    public String ask(
            @AuthenticationPrincipal User currentUser,
            @RequestParam String question,
            @RequestParam User reciever,
            //@RequestParam User reciever,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable
    ) {
        if( question != null && ! question.equals("")){
            Card card = new Card(question, currentUser, reciever);
            cardService.save(card);
        } else {
            model.addAttribute("questionError","Question text is mandatory!");
        }
        model.addAttribute("page", loadCards(pageable, currentUser, null));
        return "redirect:/channel/" + reciever.getId();
    }

    @PostMapping("answer/{card}")
    public String answer(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("answer_text") String answer,
            @PathVariable Card card,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable
    ) {
        if(!card.getReceiver().equals(currentUser)) {
            model.addAttribute("message", "You cannot answer to this question");
        } else if (! card.getAnswer().isEmpty()) {
            model.addAttribute("message", "You cannot update your answer");
        } else {
            cardService.saveAnswer(card, answer);
        }
        model.addAttribute("page", loadCards(pageable, currentUser, null));
        return "main";
    }

    @GetMapping("channel/{userChannel}")
    public String viewChannel(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User userChannel,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable
    ) {
        Page<CardDto> page = cardService.cardListForUserReceiver(pageable, currentUser,userChannel);

        model.addAttribute("userChannel", userChannel);
        model.addAttribute("page", page);
        model.addAttribute("isCurrentUser", currentUser.equals(userChannel));
        return "main";
    }

    @GetMapping("cards/{card}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Card card,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = card.getLikes();

        if(likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }

    private Page<CardDto> loadCards(Pageable pageable, User currentUser, User receiver) {
    //private void loadCards(Pageable pageable, Model model, User currentUser, User receiver) {
        if (receiver == null) {
            return cardService.cardListAll(pageable, currentUser);
            //page = cardService.cardListAll(pageable, currentUser);
        } else {
            return cardService.cardListForUserReceiver(pageable,currentUser, receiver);
            //page = cardService.cardListForUserReceiver(pageable, currentUser, receiver);
        }
    }
}
