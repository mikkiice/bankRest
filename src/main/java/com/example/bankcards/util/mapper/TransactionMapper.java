package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.request.CreateTransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.util.MaskCardNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MaskCardNumber.class)
public interface TransactionMapper {

    @Mapping(source = "fromCardId", target = "fromCard.id")
    @Mapping(source = "toCardId", target = "toCard.id")
    Transaction toEntity(CreateTransactionRequest request);

    @Mapping(source = "fromCard.cardNumber", target = "fromCardNumber", qualifiedByName = "mask")
    @Mapping(source = "toCard.cardNumber", target = "toCardNumber", qualifiedByName = "mask")
    TransactionResponse toDto(Transaction transaction);

}
