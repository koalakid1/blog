package com.tamlog.blog.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateReqeust {
    String title;
    String content;
}
