package com.tamlog.blog.api;

import com.tamlog.blog.api.dto.UserChangePasswordRequest;
import com.tamlog.blog.domain.user.UserService;
import com.tamlog.blog.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> findCurrentUser() {
        return ResponseEntity.ok(userService.findCurrentUser());
    }

    @PutMapping("nickname")
    public ResponseEntity<UserResponse> updateNickName(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(userService.updateUserNickname(request.get("nickname")));
    }

    @PutMapping("password")
    public ResponseEntity<UserResponse> updatePassword(@RequestBody UserChangePasswordRequest request) {
        return ResponseEntity.ok(userService.updateUserPassword(request));
    }
}
