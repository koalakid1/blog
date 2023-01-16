package com.tamlog.blog.repository;

import com.tamlog.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByPriorityGreaterThanEqualAndPriorityLessThan(Integer updatePriority, Integer priority);

    List<Category> findByPriorityGreaterThanAndPriorityLessThanEqual(Integer priority, Integer updatePriority);
}