package com.blinkit.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthDtos {
    @Data
    public static class RegisterRequest {
        @NotBlank private String name;
        @Email private String email;
        @NotBlank private String phone;
        @NotBlank @Size(min = 6) private String password;
    }

    @Data
    public static class LoginRequest {
        @Email private String email;
        @NotBlank private String password;
    }

    @Data
    public static class AuthResponse {
        private final String token;
        private final String role;
    }
}
