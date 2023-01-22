package com.tamlog.blog.domain.reply;

import com.tamlog.blog.domain.BaseTimeEntity;
import com.tamlog.blog.domain.board.Board;
import com.tamlog.blog.domain.user.Account;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "reply")
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}