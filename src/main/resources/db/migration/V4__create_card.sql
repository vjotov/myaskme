create table card (
    id int8 not null,
    question varchar(1024),
    answer varchar(1024),
    author_id int8,
    receiver_id int8,
    primary key (id)
);

alter table if exists card
    add constraint card_author_fk
    foreign key (author_id) references usr;

alter table if exists card
    add constraint card_receiver_fk
    foreign key (receiver_id) references usr;