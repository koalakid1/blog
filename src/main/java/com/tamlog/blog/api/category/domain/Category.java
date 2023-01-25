package com.tamlog.blog.api.category.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    protected Category() {
    }

    @Builder
    public Category(Long id, String name, Integer priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }
}