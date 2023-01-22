package com.tamlog.blog.api;

import com.tamlog.blog.domain.reply.ReplyService;
import com.tamlog.blog.domain.reply.dto.ReplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping
    public ResponseEntity<ReplyResponse> findAll() {
        return ResponseEntity.ok(replyService.findAll());
    }

}
