package com.example.bankcards.service;

import com.example.bankcards.dto.request.CreateUserRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse getUserById(Long userId);
    Page<UserResponse> getAllUsers(Pageable pageable);
    void deleteUserById(Long userId);
    UserResponse updateRoleById(Long userId, Role role);

}
