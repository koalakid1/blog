package com.tamlog.blog.dto;

import com.tamlog.blog.entity.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {

    private Long id;

    private String writer;

    private String title;

    private String content;

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    private LocalDateTime deleteDate;

    public Board toEntity() {
        return Board.builder()
                .id(this.id)
                .content(this.content)
                .title(this.title)
                .writer(this.writer)
                .registerDate(this.registerDate)
                .deleteDate(this.deleteDate)
                .updateDate(this.updateDate)
                .build();
    }

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .content(board.getContent())
                .title(board.getTitle())
                .registerDate(board.getRegisterDate())
                .deleteDate(board.getDeleteDate())
                .updateDate(board.getUpdateDate())
                .writer(board.getWriter())
                .build();
    }
}
