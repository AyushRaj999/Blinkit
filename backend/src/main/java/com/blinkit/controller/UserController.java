package com.blinkit.controller;

import com.blinkit.entity.User;
import com.blinkit.repository.UserRepository;
import com.blinkit.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final CommonService commonService;
    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<User> profile() {
        User user = commonService.currentUser();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> update(@RequestBody User payload) {
        User user = commonService.currentUser();
        user.setName(payload.getName());
        user.setPhone(payload.getPhone());
        userRepository.save(user);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
