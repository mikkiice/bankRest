package com.example.bankcards.mapper;

import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password") // зашифруем в сервисе
    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterRequest req);
}
