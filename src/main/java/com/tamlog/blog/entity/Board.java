package com.tamlog.blog.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString @Builder
public class Board {
    @Id @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @Column(name = "writer")
    private String writer;
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;
}
