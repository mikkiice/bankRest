package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.request.CreateCardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.MaskCardNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MaskCardNumber.class)
public interface CardMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "cardNumber", target = "cardNumber", qualifiedByName = "mask")
    CardResponse toDto(Card card);

    @Mapping(source = "userId", target = "user.id")
    Card toEntity(CreateCardRequest cardRequest);
}
