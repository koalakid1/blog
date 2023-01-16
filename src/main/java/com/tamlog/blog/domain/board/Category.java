package com.tamlog.blog.domain.board;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }
}