package com.blinkit.service.impl;

import com.blinkit.entity.User;
import com.blinkit.exception.ResourceNotFoundException;
import com.blinkit.repository.UserRepository;
import com.blinkit.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final UserRepository userRepository;

    @Override
    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
