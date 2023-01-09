package com.tamlog.blog.repository;

import com.tamlog.blog.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
