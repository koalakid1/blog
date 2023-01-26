package com.tamlog.blog.api.board.controller;

import com.tamlog.blog.api.board.dto.BoardRequest;
import com.tamlog.blog.api.board.dto.BoardResponse;
import com.tamlog.blog.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/categories/{categoryId}/boards")
    public ResponseEntity<List<BoardResponse>> findAll(@PathVariable Long categoryId) {
        return new ResponseEntity<>(boardService.findAll(categoryId), HttpStatus.OK);
    }

    @PostMapping("/categories/{categoryId}/boards")
    public ResponseEntity<BoardResponse> save(@PathVariable Long categoryId, @Valid @RequestBody BoardRequest request) {
        return new ResponseEntity<>(boardService.save(categoryId, request), HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> findById(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.findById(boardId), HttpStatus.OK);
    }

    @PutMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long boardId,@Valid @RequestBody BoardRequest request) {
        return ResponseEntity.ok(boardService.update(boardId, request));
    }

    @PutMapping("/boards/{boardId}/category")
    public ResponseEntity<BoardResponse> updateCategory(@PathVariable Long boardId, @RequestBody Map<String, Long> categoryId) {

        return ResponseEntity.ok(boardService.updateCategory(boardId, categoryId.get("categoryId")));
    }
}
