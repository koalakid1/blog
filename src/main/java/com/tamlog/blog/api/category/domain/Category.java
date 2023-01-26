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

    @Embedded
    private Title title;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    protected Category() {
    }

    @Builder
    public Category(Long id, String title, Integer priority) {
        this.id = id;
        this.title = new Title(title);
        this.priority = priority;
    }

    public void updateTitle(String title) {
        this.title = new Title(title);
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }
}