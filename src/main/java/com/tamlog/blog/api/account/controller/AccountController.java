package com.tamlog.blog.api.account.controller;

import com.tamlog.blog.api.account.dto.AccountChangePasswordRequest;
import com.tamlog.blog.api.account.dto.AccountResponse;
import com.tamlog.blog.api.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/profile")
    public ResponseEntity<AccountResponse> findCurrentAccount() {
        return ResponseEntity.ok(accountService.findCurrentAccount());
    }

    @PutMapping("nickname")
    public ResponseEntity<AccountResponse> updateNickName(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(accountService.updateNickname(request.get("nickname")));
    }

    @PutMapping("password")
    public ResponseEntity<AccountResponse> updatePassword(@RequestBody AccountChangePasswordRequest request) {
        return ResponseEntity.ok(accountService.updatePassword(request));
    }
}
