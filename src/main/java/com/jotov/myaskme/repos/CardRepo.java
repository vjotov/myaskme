package com.jotov.myaskme.repos;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.domain.dto.CardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepo extends JpaRepository<Card, Long> {
    @Query("select new com.jotov.myaskme.domain.dto.CardDto(c) " +
            "from Card c")
    Page<CardDto> findAll(Pageable pageable, User currentUser);

    @Query("select new com.jotov.myaskme.domain.dto.CardDto(" +
            " c " +
            " ) " +
            "from Card c "+
            "where c.receiver = :author"
    )
    Page<CardDto> findByReceiver(Pageable pageable, User currentUser, @Param("author") User user);
}
