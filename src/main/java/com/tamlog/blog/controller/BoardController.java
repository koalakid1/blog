package com.tamlog.blog.controller;

import com.tamlog.blog.dto.BoardDto;
import com.tamlog.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getBoards() {
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoardDto> postBoard(@RequestBody BoardDto boardDto) {
        return new ResponseEntity<>(boardService.postBoard(boardDto), HttpStatus.OK);
    }
}
