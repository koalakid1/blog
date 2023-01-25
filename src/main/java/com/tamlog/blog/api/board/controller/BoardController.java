package com.tamlog.blog.api.board.controller;

import com.tamlog.blog.api.board.dto.BoardResponse;
import com.tamlog.blog.api.board.dto.BoardSaveRequest;
import com.tamlog.blog.api.board.dto.BoardUpdateReqeust;
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
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardResponse>> findAll() {
        return new ResponseEntity<>(boardService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoardResponse> save(@Valid @RequestBody BoardSaveRequest request) {
        return new ResponseEntity<>(boardService.save(request), HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> findById(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.findById(boardId), HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long boardId,@Valid @RequestBody BoardUpdateReqeust request) {
        return ResponseEntity.ok(boardService.update(boardId, request));
    }

    @PutMapping("/{boardId}/category")
    public ResponseEntity<BoardResponse> updateCategory(@PathVariable Long boardId, @RequestBody Map<String, Long> categoryId) {

        return ResponseEntity.ok(boardService.updateCategory(boardId, categoryId.get("categoryId")));
    }
}
