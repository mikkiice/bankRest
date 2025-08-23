package com.example.bankcards.util;

import org.mapstruct.Named;

public class MaskCardNumber {
    @Named("mask")
    public String maskCardNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
