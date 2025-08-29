package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.CreateTransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) {
        return null;
    }
}
