package com.example.bankcards.validator;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.InvalidTransactionException;
import com.example.bankcards.exception.NotEnoughFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionValidator {

    public void validateTransactionCards(Card fromCard, Card toCard, BigDecimal amount) {
        validateNotSameCard(fromCard.getId(), toCard.getId());
        validateTransactionAllowed(fromCard, toCard);
        validateSufficientFunds(fromCard, amount);
    }

    /**
     * Проверка перевода с одной карты на ту же
     */
    private void validateNotSameCard(Long fromCardId, Long toCardId) {
        if (fromCardId.equals(toCardId)) {
            throw new InvalidTransactionException("Нельзя переводить на ту же карту");
        }
    }

    /**
     * Проверка, что баланс больше отправляемой суммы
     */
    private void validateSufficientFunds(Card fromCard, BigDecimal amount) {
        if(fromCard.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsException("На карте недостаточно средств: " + fromCard.getBalance());
        }
    }

    /**
     * Проверка, что обе карты активны
     */
    private void validateTransactionAllowed(Card fromCard, Card toCard) {
        if (fromCard.getStatus() != CardStatus.ACTIVE || toCard.getStatus() != CardStatus.ACTIVE) {
            throw new InvalidTransactionException("Обе карты должны быть активны. Отправитель: " + fromCard.getStatus() +
                    ", Получатель: " + toCard.getStatus());
        }

    }
}
