package com.example.bankcards.dto.response;

import com.example.bankcards.entity.Role;

public record LoginResponse(
        String token,
        String username,
        Role role
) {
}
