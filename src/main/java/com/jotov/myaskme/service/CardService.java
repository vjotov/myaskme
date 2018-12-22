package com.jotov.myaskme.service;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.domain.dto.CardDto;
import com.jotov.myaskme.repos.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    private CardRepo cardRepo;
    public void save(Card card) {
        cardRepo.save(card);
    }

    public Iterable<Card> cardListAll(User currentUser) {
    //public Page<Card> cardListAll(Pageable pageable, User currentUser) {
        return cardRepo.findAll();
    }

    public Iterable<Card> cardListForUserReceiver( User currentUser, User receiver) {
    //public Page<Card> cardListForUserReceiver(Pageable pageable, User currentUser, User receiver) {
        return cardRepo.findByReceiver(receiver);
    }

    public void saveAnswer(Card card, String answer) {
        card.setAnswer(answer);
        cardRepo.save(card);
    }
}
