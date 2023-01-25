package com.tamlog.blog.api.board.domain;

import com.tamlog.blog.advice.UnauthorizedException;
import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.reply.domain.Reply;
import com.tamlog.blog.support.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.tamlog.blog.support.SecurityUtil.getCurrentAccountId;

@Entity
@Getter
@DynamicInsert
@Table(name = "board")
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

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

    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    protected Board() {
    }

    @Builder
    public Board(String title, String content, Integer hit, Integer like, Category category, Account account, List<Reply> replies) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.hit = hit;
        this.like = like;
        this.category = category;
        this.account = account;
        this.replies = replies;
    }

    public void update(String title, String content){
        if (!this.account.getId().equals(getCurrentAccountId())) {
            throw new UnauthorizedException();
        }
        this.title = new Title(title);
        this.content = new Content(content);
    }
    public void updateCategory(Category category) {
        this.category = category;
    }

    public void validateAccount() {

    }
}