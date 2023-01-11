package com.tamlog.blog.controller;

import com.tamlog.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

//    @GetMapping
//    public ResponseEntity<List<BoardDto>> getBoards() {
//        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<BoardDto> postBoard(@RequestBody BoardDto boardDto) {
//        return new ResponseEntity<>(boardService.postBoard(boardDto), HttpStatus.OK);
//    }
}
