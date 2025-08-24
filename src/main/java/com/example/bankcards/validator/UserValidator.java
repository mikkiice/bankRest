package com.example.bankcards.validator;

import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUsernameUniq(String username) {
        if(userRepository.existsByUsername(username)){
            throw new UserAlreadyExistsException("Username already taken: " + username);
        }
    }
}
