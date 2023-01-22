package com.tamlog.blog.domain.board;

import com.tamlog.blog.domain.BaseTimeEntity;
import com.tamlog.blog.domain.user.Account;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@DynamicInsert
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
    @Builder.Default()
    @Column(name = "hit_cnt", insertable = false)
    private Integer hit = 0;

    @ColumnDefault("0")
    @Builder.Default()
    @Column(name = "like_cnt", insertable = false)
    private Integer like = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}