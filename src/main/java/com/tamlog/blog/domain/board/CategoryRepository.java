package com.tamlog.blog.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByPriorityGreaterThanEqualAndPriorityLessThan(Integer updatePriority, Integer priority);

    List<Category> findByPriorityGreaterThanAndPriorityLessThanEqual(Integer priority, Integer updatePriority);

    List<Category> findByPriorityGreaterThanEqual(Integer priority);

    Optional<Category> findByName(String categoryName);
}