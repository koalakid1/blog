package com.tamlog.blog.domain.board;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "category")
@DynamicInsert
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "priority", nullable = false, insertable = false)
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Integer priority;

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }
}