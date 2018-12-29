create table card_likes(
    user_id bigint not null references usr,
    card_id bigint not null references card,
    primary key(user_id, card_id)
)