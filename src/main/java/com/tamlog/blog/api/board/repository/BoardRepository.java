package com.tamlog.blog.api.board.repository;

import com.tamlog.blog.api.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
