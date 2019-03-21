package com.jotov.myaskme.dto;


import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;

public class CardDto {
    private Long id;
    private String question;
    private String answer;
    private User receiver;
    private Long likes;
    private Boolean meLiked;

    public CardDto(Card card, Long likes, Boolean meLiked) {
        this.id = card.getId();
        this.question = card.getQuestion();
        this.answer = card.getAnswer();
        this.receiver = card.getReceiver();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public User getReceiver() {
        return receiver;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", receiver=" + receiver +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}