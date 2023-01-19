package com.tamlog.blog.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByPriorityGreaterThanEqualAndPriorityLessThan(Integer updatePriority, Integer priority);

    List<Category> findByPriorityGreaterThanAndPriorityLessThanEqual(Integer priority, Integer updatePriority);

    List<Category> findByPriorityGreaterThanEqual(Integer priority);
}