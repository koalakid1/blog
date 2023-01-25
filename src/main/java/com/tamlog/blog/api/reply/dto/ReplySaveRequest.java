package com.tamlog.blog.api.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplySaveRequest {
    String content;

    Long boardId;
}
