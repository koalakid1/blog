package com.tamlog.blog.api.board.service;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.exception.AccountNotFoundException;
import com.tamlog.blog.api.account.repository.AccountRepository;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.board.dto.BoardRequest;
import com.tamlog.blog.api.board.dto.BoardResponse;
import com.tamlog.blog.api.board.exception.BoardNotFoundException;
import com.tamlog.blog.api.board.repository.BoardRepository;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.exception.CategoryNotFoundException;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_ID;
import static com.tamlog.blog.support.SecurityUtil.getCurrentAccountId;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public BoardResponse save(Long categoryId, BoardRequest request) {
        Account account = accountRepository.findById(getCurrentAccountId())
                .orElseThrow(AccountNotFoundException::new);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));

        Board board = request.toEntity(category, account);

        return new BoardResponse(boardRepository.save(board));
    }

    public List<BoardResponse> findAll(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));

        return boardRepository.findAllByCategory(category).stream()
                .map(BoardResponse::new)
                .toList();
    }

    public BoardResponse findById(Long id) {
        return new BoardResponse(boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(EXCEPTION_ID, id)));
    }

    @Transactional
    public BoardResponse update(Long id, BoardRequest request) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(EXCEPTION_ID, id));

        board.update(request.getTitle(), request.getContent(), getCurrentAccountId());

        return new BoardResponse(board);
    }

    @Transactional
    public BoardResponse updateCategory(Long boardId, Long categoryId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(EXCEPTION_ID, boardId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(EXCEPTION_ID, categoryId));

        board.updateCategory(category, getCurrentAccountId());

        return new BoardResponse(board);
    }
}
