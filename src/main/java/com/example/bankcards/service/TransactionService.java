package com.example.bankcards.service;

import com.example.bankcards.dto.request.CreateTransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest);

}
