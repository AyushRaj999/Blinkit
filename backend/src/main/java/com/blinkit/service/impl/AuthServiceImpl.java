package com.blinkit.service.impl;

import com.blinkit.dto.AuthDtos;
import com.blinkit.entity.Role;
import com.blinkit.entity.User;
import com.blinkit.exception.ApiException;
import com.blinkit.repository.UserRepository;
import com.blinkit.security.JwtService;
import com.blinkit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already exists");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthDtos.AuthResponse(token, user.getRole().name());
    }

    @Override
    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ApiException("Invalid login"));
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthDtos.AuthResponse(token, user.getRole().name());
    }
}
