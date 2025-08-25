package com.example.bankcards.validator;

import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    /**
     * Проверка логина на уникальность
     */
    public void validateUsernameUnique(String username) {
        if(userRepository.existsByUsername(username)){
            throw new UserAlreadyExistsException("Логин занят: " + username);
        }
    }

    /**
     * Проверка на существование юзера
     */
    public void validateUserExists(String username) {
        if(!userRepository.existsByUsername(username)){
            throw new UserNotFoundException("Пользователь с таким логином не найден : " + username);
        }
    }
}
