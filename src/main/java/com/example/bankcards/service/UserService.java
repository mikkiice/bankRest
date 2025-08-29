package com.example.bankcards.service;

import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse createUser(RegisterRequest request);
    UserResponse getUserById(Long userId);
    Page<UserResponse> getAllUsers(Pageable pageable);
    void deleteUserById(Long userId);
    UserResponse updateRoleById(Long userId, Role role);

}
