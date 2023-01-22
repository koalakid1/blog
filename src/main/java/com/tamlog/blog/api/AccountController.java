package com.tamlog.blog.api;

import com.tamlog.blog.api.dto.AccountChangePasswordRequest;
import com.tamlog.blog.domain.user.AccountService;
import com.tamlog.blog.domain.user.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<AccountResponse> findCurrentUser() {
        return ResponseEntity.ok(accountService.findCurrentUser());
    }

    @PutMapping("nickname")
    public ResponseEntity<AccountResponse> updateNickName(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(accountService.updateUserNickname(request.get("nickname")));
    }

    @PutMapping("password")
    public ResponseEntity<AccountResponse> updatePassword(@RequestBody AccountChangePasswordRequest request) {
        return ResponseEntity.ok(accountService.updateUserPassword(request));
    }
}
