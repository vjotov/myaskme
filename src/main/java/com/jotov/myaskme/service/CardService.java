package com.jotov.myaskme.service;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.repos.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    private CardRepo cardRepo;
    public void save(Card card) {
        cardRepo.save(card);
    }
}
