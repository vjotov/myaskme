package com.jotov.myaskme.domain;

import javax.persistence.*;

@Entity
public class Card {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_id")
    private User receiver;
}
