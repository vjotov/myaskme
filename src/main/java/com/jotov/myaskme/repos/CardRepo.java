package com.jotov.myaskme.repos;

import com.jotov.myaskme.domain.Card;
import com.jotov.myaskme.domain.User;
import com.jotov.myaskme.dto.CardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepo extends JpaRepository<Card, Long> {
    @Query("select new com.jotov.myaskme.dto.CardDto(" +
            " c, " +
            " count(cl)," +
            " sum(case when cl = :user then 1 else 0 end) > 0 " +
            " ) " +
            " from Card c left join c.likes cl " +
            " group by c")
    Page<CardDto> findAll(Pageable pageable, @Param("user") User currentUser);

    @Query("select new com.jotov.myaskme.dto.CardDto(" +
            " c," +
            " count(cl)," +
            " sum(case when cl = :user then 1 else 0 end) > 0" +
            " )" +
            " from Card c left join c.likes cl "+
            " where c.receiver = :receiver " +
            " group by c")
    Page<CardDto> findByReceiver(Pageable pageable, @Param("user") User currentUser, @Param("receiver") User receiver);
}
