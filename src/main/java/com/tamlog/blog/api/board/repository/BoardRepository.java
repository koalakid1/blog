package com.tamlog.blog.api.board.repository;

import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByCategory(Category category);
    Boolean existsByCategoryId(Long categoryId);
}
