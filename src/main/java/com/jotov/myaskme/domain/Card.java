package com.jotov.myaskme.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_id")
    private User receiver;

    @ManyToMany
    @JoinTable(
            name = "card_likes",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    public Card() {
    }

    public Card(String question, User author, User receiver) {
        this.question = question;
        this.author = author;
        this.receiver = receiver;
    }
}
