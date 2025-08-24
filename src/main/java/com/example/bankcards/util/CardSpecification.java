package com.example.bankcards.util;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class CardSpecification {
    public static Specification<Card> hasStatus(CardStatus cardStatus) {
        return (root, cq, cb) ->
                cardStatus == null ? cb.conjunction() : cb.equal(root.get("status"), cardStatus);
    }
    public static Specification<Card> hasMinBalance(BigDecimal minBalance) {
        return (root, cq, cb) ->
                minBalance == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("balance"), minBalance);
    }

    public static Specification<Card> hasMaxBalance(BigDecimal maxBalance) {
        return (root, cq, cb) ->
                maxBalance == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("balance"), maxBalance);
    }
}
