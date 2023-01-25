package com.tamlog.blog.api.reply.domain;

import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.support.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "reply")
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id", nullable = false)
    private Long id;

    @Embedded
    private Content content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Reply parentReply;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    protected Reply() {

    }

    @Builder
    public Reply(Long id, String content, Reply parentReply, Board board, Account account) {
        this.id = id;
        this.content = new Content(content);
        this.parentReply = parentReply;
        this.board = board;
        this.account = account;
    }
}