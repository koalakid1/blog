package com.tamlog.blog.api.board.service;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.exception.AccountNotFoundException;
import com.tamlog.blog.api.account.repository.AccountRepository;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.board.dto.BoardResponse;
import com.tamlog.blog.api.board.dto.BoardSaveRequest;
import com.tamlog.blog.api.board.dto.BoardUpdateReqeust;
import com.tamlog.blog.api.board.exception.BoardNotFoundException;
import com.tamlog.blog.api.board.repository.BoardRepository;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.exception.CategoryNotFoundException;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.tamlog.blog.support.ExceptionUtil.EXCEPTION_ID;
import static com.tamlog.blog.support.ExceptionUtil.EXCEPTION_NAME;
import static com.tamlog.blog.support.SecurityUtil.getCurrentAccountId;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public BoardResponse save(BoardSaveRequest request) {
        Account account = accountRepository.findById(getCurrentAccountId())
                .orElseThrow(AccountNotFoundException::new);

        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_NAME, request.getCategoryName()));

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
                .orElseThrow(() -> new BoardNotFoundException(EXCEPTION_ID, id)));
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateReqeust request) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(EXCEPTION_ID, id));

        board.update(request.getTitle(), request.getContent());

        return new BoardResponse(board);
    }

    @Transactional
    public BoardResponse updateCategory(Long boardId, Long categoryId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(EXCEPTION_ID, boardId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));

        board.updateCategory(category);

        return new BoardResponse(board);
    }
}
