package com.jotov.myaskme.repos;

import com.jotov.myaskme.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card, Long> {
}
