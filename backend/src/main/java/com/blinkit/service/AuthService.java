package com.blinkit.service;

import com.blinkit.dto.AuthDtos;

public interface AuthService {
    AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request);
    AuthDtos.AuthResponse login(AuthDtos.LoginRequest request);
}
