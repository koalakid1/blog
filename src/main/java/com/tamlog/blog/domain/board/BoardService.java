package com.tamlog.blog.domain.board;

import com.tamlog.blog.api.dto.BoardResponse;
import com.tamlog.blog.api.dto.BoardSaveRequest;
import com.tamlog.blog.api.dto.BoardUpdateReqeust;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse save(BoardSaveRequest request) {
        return new BoardResponse(boardRepository.save(request.toEntity()));
    }

    public List<BoardResponse> findAll() {
        return boardRepository.findAll().stream().map(BoardResponse::new).toList();
    }

    public BoardResponse findById(Long id) {
        return new BoardResponse(boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id)));
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateReqeust request) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        board.update(request.getTitle(), request.getContent());

        return new BoardResponse(board);
    }
}
