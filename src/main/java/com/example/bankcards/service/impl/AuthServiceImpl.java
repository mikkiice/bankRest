package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.AuthRequest;
import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.dto.response.AuthResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.AuthMapper;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.RefreshTokenRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse login(AuthRequest request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.username(), request.password())
       );

       User user = userRepository.findByUsername(request.username())
               .orElseThrow(() -> new UserNotFoundException("Пользователь не найден: " + request.username()));


       UserDetails ud = springUser(user);
       String token = jwtService.generateAccessToken(ud, Map.of("uid", user.getId()));

        return new AuthResponse(token, "Bearer");
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsernameIgnoreCase(registerRequest.username())) {
            throw new UserAlreadyExistsException("Этот логин занят" + registerRequest.username());
        }

        User user = authMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);

        UserDetails ud = springUser(user);
        String token = jwtService.generateAccessToken(ud, Map.of("uid", user.getId()));

        return new AuthResponse(token, "Bearer");
    }



    private UserDetails springUser(User user) {
        var auth = user.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).toList();
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(auth)
                .build();
    }
}
