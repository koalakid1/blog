package com.tamlog.blog.api.category.repository;

import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByPriorityGreaterThanEqualAndPriorityLessThan(Integer updatePriority, Integer priority);

    List<Category> findByPriorityGreaterThanAndPriorityLessThanEqual(Integer priority, Integer updatePriority);

    List<Category> findByPriorityGreaterThanEqual(Integer priority);

    Optional<Category> findByTitle(Title title);
}