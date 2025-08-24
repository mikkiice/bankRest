package com.example.bankcards.service;

import com.example.bankcards.dto.request.CardFilterRequest;
import com.example.bankcards.dto.request.CreateCardRequest;
import com.example.bankcards.dto.request.UpdateCardStatusRequest;
import com.example.bankcards.dto.response.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {
    CardResponse createCard(CreateCardRequest request);
    CardResponse updateCardStatus(UpdateCardStatusRequest request);
    void deleteCard(Long cardId);
    CardResponse getCardById(Long cardId);
    Page<CardResponse> getAllCards(Pageable pageable);
    Page<CardResponse> getCardsByUsername(String username, Pageable pageable);
    Page<CardResponse> getCardsByFilter(CardFilterRequest cardFilterRequest, Pageable pageable);
}
