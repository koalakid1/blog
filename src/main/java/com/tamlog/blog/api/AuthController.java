package com.tamlog.blog.api;

import com.tamlog.blog.api.dto.AccountRequest;
import com.tamlog.blog.domain.user.AuthService;
import com.tamlog.blog.domain.user.dto.AccountResponse;
import com.tamlog.blog.domain.user.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AccountResponse> signup(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
