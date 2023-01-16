package com.tamlog.blog.api;

import com.tamlog.blog.api.dto.BoardResponse;
import com.tamlog.blog.api.dto.BoardSaveRequest;
import com.tamlog.blog.api.dto.BoardUpdateReqeust;
import com.tamlog.blog.domain.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<BoardResponse> save(@RequestBody BoardSaveRequest request) {
        return new ResponseEntity<>(boardService.save(request), HttpStatus.OK);
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<BoardResponse> findById(@PathVariable Long board_id) {
        return new ResponseEntity<>(boardService.findById(board_id), HttpStatus.OK);
    }

    @PutMapping("/{board_id}")
    public Long update(@PathVariable Long board_id, @RequestBody BoardUpdateReqeust request) {
        return ResponseEntity.ok(boardService.update(board_id, request));
    }
}
