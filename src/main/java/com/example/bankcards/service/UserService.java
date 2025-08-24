package com.example.bankcards.service;

import com.example.bankcards.dto.request.CreateUserRequest;
import com.example.bankcards.dto.request.LoginRequest;
import com.example.bankcards.dto.response.LoginResponse;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse createUser(CreateUserRequest createUserRequest);
    LoginResponse login(LoginRequest loginRequest);
    UserResponse getUserById(Long userId);
    Page<UserResponse> getAllUsers(Pageable pageable);
    void deleteUserById(Long userId);
    UserResponse updateRoleById(Long userId, Role role);

}
