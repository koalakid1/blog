package com.tamlog.blog.api.board.domain;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.support.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
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

    protected Board() {
    }

    @Builder
    public Board(Long id, String title, String content, Integer hit, Integer like, Category category, Account account) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.like = like;
        this.category = category;
        this.account = account;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}