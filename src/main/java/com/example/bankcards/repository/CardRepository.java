package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Page<Card> findAllByUser_Username(String userUsername, Pageable pageable);
    Card findByCardNumber(String cardNumber);
    Page<Card> findAllByUser_UsernameAndStatus(String username, CardStatus status, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")
    })
    @Query("select c from Card c where c.id in :ids order by c.id asc")
    List<Card> findAllByIdInForUpdateOrderByIdAsc(@Param("ids") Collection<Long> ids);

}
