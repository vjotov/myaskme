package com.jotov.myaskme.repos;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepo extends JpaRepository<Card, Long> {
//    @Query("select new com.jotov.myaskme.domain.dto.CardDto(c) " +
//            "from Card c" +
//            "group by ")
//    Page<CardDto> findAllDto(Pageable pageable);//, User currentUser);
//
//    @Query("select new com.jotov.myaskme.domain.dto.CardDto(c)" +
//            "from Card c "+
//            "where c.receiver = :receiver"
//    )
//    Page<CardDto> findByReceiver(Pageable pageable, @Param("receiver") User user);
    Page<Card> findByReceiver(Pageable pageable, @Param("receiver") User user);
    Iterable<Card> findByReceiver(@Param("receiver") User user);
}
