package com.tamlog.blog.service;

import com.tamlog.blog.dto.BoardDto;
import com.tamlog.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto postBoard(BoardDto boardDto) {
        return BoardDto.of(boardRepository.save(boardDto.toEntity()));
    }

    public List<BoardDto> getBoards() {
        return boardRepository.findAll().stream().map(board -> BoardDto.of(board)).toList();
    }
}
