package com.example.bankcards.validator;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Role;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.CardOwnershipException;
import com.example.bankcards.exception.InvalidCardStatusException;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CardValidator {

    private final CardRepository cardRepository;

    /**
     * Проверка, что карта существует.
     */
    public Card validateCardExists(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Карта не найдена: " +cardId));
    }

    /**
     * Проверка принадлежности карты пользователю.
     */
    public void validateCardOwnership(Long cardId, Long userId) {
        Card card = validateCardExists(cardId);
        if(!userId.equals(card.getUser().getId())) {
            throw new CardOwnershipException("Карта " + cardId + " не принадлежит пользователю " + userId);
        }
    }

    /**
     * Проверка, что карта активна.
     */
    public void validateCardActive(Card card) {
        if(card.getStatus() != CardStatus.ACTIVE) {
            throw new InvalidCardStatusException("Карта не активна: " + card.getStatus());
        }
    }

    /**
     * Проверка, что карта не просрочена.
     */
    public void validateCardNotExpired(Card card) {
        if(card.getExpirationDate().isBefore(LocalDate.now())) {
            throw new InvalidCardStatusException("Карта не действительна: " + card.getExpirationDate());
        }
    }

    /**
     * Проверка, что пользователь правильно запрашивает смену статуса карты
     */
    public void validateCardStatusChanged(Card card, CardStatus newStatus, Role role) {
        if (role == Role.USER) {
            if (!(card.getStatus() == CardStatus.ACTIVE && newStatus == CardStatus.BLOCKED)) {
                throw new InvalidCardStatusException("Пользователь может запросить только блокировку активной карты, а текущий статус: " + card.getStatus());
            }
        }
    }
}
