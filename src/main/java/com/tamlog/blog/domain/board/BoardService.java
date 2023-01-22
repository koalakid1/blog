package com.tamlog.blog.domain.board;

import com.tamlog.blog.api.dto.BoardSaveRequest;
import com.tamlog.blog.api.dto.BoardUpdateReqeust;
import com.tamlog.blog.common.exception.CustomException;
import com.tamlog.blog.common.exception.ErrorCode;
import com.tamlog.blog.common.util.SecurityUtil;
import com.tamlog.blog.domain.board.dto.BoardResponse;
import com.tamlog.blog.domain.user.Account;
import com.tamlog.blog.domain.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public BoardResponse save(BoardSaveRequest request) {
        Account account = accountRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));

        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER));

        Board board = request.toEntity(category, account);

        return new BoardResponse(boardRepository.save(board));
    }

    public List<BoardResponse> findAll() {
        return boardRepository.findAll().stream()
                .map(BoardResponse::new)
                .toList();
    }

    public BoardResponse findById(Long id) {
        return new BoardResponse(boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER)));
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateReqeust request) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST_PARAMETER));

        board.update(request.getTitle(), request.getContent());

        return new BoardResponse(board);
    }

    @Transactional
    public BoardResponse updateCategory(Long boardId, Long categoryId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID));

        board.updateCategory(category);

        return new BoardResponse(board);
    }
}
