package com.tamlog.blog.api.auth.controller;

import com.tamlog.blog.api.account.dto.AccountRequest;
import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.auth.dto.TokenResponse;
import com.tamlog.blog.api.auth.service.AuthService;
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
