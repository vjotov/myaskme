package com.jotov.myaskme.domain.dto;


import com.jotov.myaskme.domain.User;

public class CardDto {
    private Long id;
    private String question;
    private String answer;
    private User author;
    private User receiver;

    public CardDto(Long id, String question, String answer, User author, User receiver) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.author = author;
        this.receiver = receiver;
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

    public User getAuthor() {
        return author;
    }

    public User getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                //", likes=" + likes +
                //", meLiked=" + meLiked +
                '}';
    }
}