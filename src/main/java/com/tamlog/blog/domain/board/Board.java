package com.tamlog.blog.domain.board;

import com.tamlog.blog.domain.BaseTimeEntity;
import com.tamlog.blog.domain.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @ColumnDefault("0")
    @Column(name = "hit")
    private Integer hit;

    @ColumnDefault("0")
    @Column(name = "like")
    private Integer like;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}